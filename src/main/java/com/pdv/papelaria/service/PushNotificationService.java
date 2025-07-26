package com.pdv.papelaria.service;

import com.pdv.papelaria.dto.PushSubscriptionDTO;
import com.pdv.papelaria.repository.PushSubscriptionRepository;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Security;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.util.List;

@Service
public class PushNotificationService {

    private static final Logger log = LoggerFactory.getLogger(PushNotificationService.class);

    // ✅ Bloco estático para registrar o BouncyCastle Provider
    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    @Value("${vapid.public.key}")
    private String publicKey;

    @Value("${vapid.private.key}")
    private String privateKey;

    @Autowired
    private PushSubscriptionRepository repository;

    public void enviarNotificacao(String titulo, String corpo) {
        log.info("📣 Iniciando envio de notificação: título='{}', corpo='{}'", titulo, corpo);

        List<PushSubscriptionDTO> subscriptions = repository.listarTodas();
        log.debug("🔍 Total de inscrições encontradas: {}", subscriptions.size());

        if (subscriptions.isEmpty()) {
            log.warn("⚠️ Nenhuma assinatura de push encontrada. Notificação não enviada.");
            return;
        }

        PushService pushService;
        try {
            pushService = new PushService()
                    .setPublicKey(publicKey)
                    .setPrivateKey(privateKey)
                    .setSubject("mailto:williampeeh@gmail.com");
            log.debug("🔐 PushService configurado com sucesso");
        } catch (Exception e) {
            log.error("❌ Erro ao configurar PushService", e);
            return;
        }

        for (PushSubscriptionDTO dto : subscriptions) {
            try {
                String payloadJson = String.format("{\"title\":\"%s\",\"body\":\"%s\"}", titulo, corpo);
                log.debug("📦 Enviando para endpoint: {}", dto.getEndpoint());
                log.trace("📬 Payload: {}", payloadJson);

                Notification notification = new Notification(
                        dto.getEndpoint(),
                        dto.getKeys().getP256dh(),
                        dto.getKeys().getAuth(),
                        payloadJson
                );

                pushService.send(notification);
                log.info("✅ Notificação enviada com sucesso para: {}", dto.getEndpoint());

            } catch (Exception e) {
                log.error("❌ Erro ao enviar notificação para: {}", dto.getEndpoint(), e);
            }
        }
    }
}
