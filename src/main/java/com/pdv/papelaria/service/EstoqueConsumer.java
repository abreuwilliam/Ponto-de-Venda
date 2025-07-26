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
        try {
            String descricao = payload.get("descricao") != null ? payload.get("descricao").toString() : "Produto desconhecido";
            int quantidade = 0;

            Object quantidadeObj = payload.get("quantidade");
            if (quantidadeObj instanceof Integer) {
                quantidade = (Integer) quantidadeObj;
            } else if (quantidadeObj instanceof String) {
                quantidade = Integer.parseInt((String) quantidadeObj);
            }

            String mensagem = String.format("🚨 Estoque baixo: %s - %d unidades!", descricao, quantidade);
            pushNotificationService.enviarNotificacao("Estoque Baixo", mensagem);

        } catch (Exception e) {
            System.err.println("❌ Erro ao processar mensagem do RabbitMQ:");
            e.printStackTrace();
        }
    }
}
