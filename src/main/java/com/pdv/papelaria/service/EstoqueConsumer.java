package com.pdv.papelaria.service;

import com.pdv.papelaria.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class EstoqueConsumer {

    @Autowired
    private PushNotificationService pushNotificationService;

    @RabbitListener(queues = RabbitMQConfig.FILA_ESTOQUE_BAIXO)
    public void receberAlertaEstoqueBaixo(Map<String, Object> payload) {
        String descricao = (String) payload.get("descricao");
        int quantidade = (int) payload.get("quantidade");

        String mensagem = String.format("🚨 Estoque baixo: %s - %d unidades!", descricao, quantidade);

        pushNotificationService.enviarNotificacao("Estoque Baixo", mensagem);
    }
}

