package com.pdv.papelaria.service;

import com.pdv.papelaria.dto.PushSubscriptionDTO;
import com.pdv.papelaria.repository.PushSubscriptionRepository;
import jakarta.annotation.PostConstruct;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.security.KeyFactory;
import java.security.Security;
import java.security.interfaces.ECPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.Instant;
import java.util.Base64;
import java.util.List;

@Service
public class PushNotificationService {

    private static final Logger log = LoggerFactory.getLogger(PushNotificationService.class);
    private static final String FCM_AUDIENCE = "https://fcm.googleapis.com";

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${vapid.public.key}")
    private String publicKey;

    @Value("${vapid.private.key}")
    private String privateKey;

    private ECPrivateKey ecPrivateKey;

    private final PushSubscriptionRepository repository;

    public PushNotificationService(PushSubscriptionRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    public void init() {
        Security.addProvider(new BouncyCastleProvider());
        try {
            byte[] decodedPrivate = Base64.getUrlDecoder().decode(privateKey);

            // Usando chave crua com Bouncy Castle
            org.bouncycastle.asn1.sec.ECPrivateKey bcPrivateKey =
                    new org.bouncycastle.asn1.sec.ECPrivateKey(
                            new java.math.BigInteger(1, decodedPrivate)
                    );

            byte[] pkcs8Bytes = new org.bouncycastle.asn1.pkcs.PrivateKeyInfo(
                    new org.bouncycastle.asn1.x509.AlgorithmIdentifier(
                            org.bouncycastle.asn1.x9.X9ObjectIdentifiers.id_ecPublicKey,
                            org.bouncycastle.asn1.nist.NISTNamedCurves.getOID("P-256")
                    ),
                    bcPrivateKey
            ).getEncoded();

            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pkcs8Bytes);
            KeyFactory kf = KeyFactory.getInstance("EC");
            ecPrivateKey = (ECPrivateKey) kf.generatePrivate(keySpec);

            log.info("✅ Chave privada VAPID carregada com sucesso.");
        } catch (Exception e) {
            log.error("❌ Erro ao decodificar chave privada VAPID", e);
        }
    }


    public void enviarNotificacao(String titulo, String corpo) {
        log.info("📣 Enviando notificação: '{}'", titulo);
        List<PushSubscriptionDTO> subscriptions = repository.listarTodas();

        if (subscriptions.isEmpty()) {
            log.warn("⚠️ Nenhuma assinatura encontrada.");
            return;
        }

        for (PushSubscriptionDTO dto : subscriptions) {
            try {
                JwtClaims claims = new JwtClaims();
                claims.setAudience(FCM_AUDIENCE);
                claims.setExpirationTimeMinutesInTheFuture(5);
                claims.setIssuedAtToNow();
                claims.setIssuer("mailto:williampeeh@gmail.com");

                JsonWebSignature jws = new JsonWebSignature();
                jws.setPayload(claims.toJson());
                jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.ECDSA_USING_P256_CURVE_AND_SHA256);
                jws.setKey(ecPrivateKey);
                jws.setHeader("typ", "JWT");

                String jwt = jws.getCompactSerialization();

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.set("Authorization", "vapid t=" + jwt + ", k=" + publicKey);
                headers.set("TTL", "60");

                String payload = String.format("""
                          {
                            "data": {
                              "title": "%s",
                              "body": "%s",
                              "icon": "https://cdn-icons-png.flaticon.com/512/1827/1827392.png"
                            }
                          }
                        """, titulo, corpo);


                HttpEntity<String> request = new HttpEntity<>(payload, headers);
                ResponseEntity<String> response = restTemplate.postForEntity(dto.getEndpoint(), request, String.class);

                log.info("✅ Enviado para {}: {} - {}", dto.getEndpoint(), response.getStatusCode(), response.getBody());

            } catch (Exception e) {
                log.error("❌ Erro ao enviar notificação para: {}", dto.getEndpoint(), e);
            }
        }
    }
}