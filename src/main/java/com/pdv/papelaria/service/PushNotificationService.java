package com.pdv.papelaria.service;


import com.pdv.papelaria.dto.PushSubscriptionDTO;
import com.pdv.papelaria.repository.PushSubscriptionRepository;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;

@Service
public class PushNotificationService {

    @Value("${vapid.public.key}")
    private String publicKey;

    @Value("${vapid.private.key}")
    private String privateKey;

    @Autowired
    private PushSubscriptionRepository repository;

    public void enviarNotificacao(String titulo, String corpo) {
        PushSubscriptionDTO dto = repository.buscar();

        if (dto == null) return;

        try {
            Notification notification = new Notification(
                    dto.getEndpoint(),
                    dto.getKeys().getP256dh(),
                    dto.getKeys().getAuth(),
                    String.format("{\"title\":\"%s\",\"body\":\"%s\"}", titulo, corpo)
            );

            PushService pushService = new PushService()
                    .setPublicKey(publicKey)
                    .setPrivateKey(privateKey)
                    .setSubject("williampeeh@gmail.com");

            pushService.send(notification);

        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeySpecException e) {
            e.printStackTrace(); // ou log.error("Erro ao criar Notification", e);
        } catch (Exception e) {
            e.printStackTrace(); // captura erro geral, como IO ou falha na rede
        }
    }
}
