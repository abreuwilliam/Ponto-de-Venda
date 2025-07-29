package com.pdv.papelaria.controller;

import com.pdv.papelaria.service.PushNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
public class PushNotificationTestController {

    @Autowired
    private PushNotificationService pushNotificationService;

    @PostMapping("/teste")
    public ResponseEntity<String> enviarNotificacaoTeste(@RequestBody MensagemDTO dto) {
        pushNotificationService.enviarNotificacao(dto.getTitulo(), dto.getCorpo());
        return ResponseEntity.ok("Notificação enviada (ou tentativa feita)");
    }

    // DTO para enviar os dados da notificação
    public static class MensagemDTO {
        private String titulo;
        private String corpo;

        public String getTitulo() { return titulo; }
        public void setTitulo(String titulo) { this.titulo = titulo; }

        public String getCorpo() { return corpo; }
        public void setCorpo(String corpo) { this.corpo = corpo; }
    }
}

