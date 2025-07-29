package com.pdv.papelaria.service;

import com.pdv.papelaria.config.RabbitMQConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class EstoqueConsumer {

    private static final Logger log = LoggerFactory.getLogger(EstoqueConsumer.class);

    @Autowired
    private PushNotificationService pushNotificationService;

    @RabbitListener(queues = RabbitMQConfig.FILA_ESTOQUE_BAIXO)
    public void receberAlertaEstoqueBaixo(Map<String, Object> payload) {
        log.info("📥 Mensagem recebida da fila '{}': {}", RabbitMQConfig.FILA_ESTOQUE_BAIXO, payload);

        try {
            String descricao = payload.get("descricao") != null
                    ? payload.get("descricao").toString()
                    : "Produto desconhecido";

            int quantidade = 0;
            Object quantidadeObj = payload.get("quantidade");

            if (quantidadeObj instanceof Integer) {
                quantidade = (Integer) quantidadeObj;
            } else if (quantidadeObj instanceof Long) {
                quantidade = ((Long) quantidadeObj).intValue();
            } else if (quantidadeObj instanceof String) {
                quantidade = Integer.parseInt((String) quantidadeObj);
            }

            log.info("🔎 Processando alerta de estoque: descrição='{}', quantidade={}", descricao, quantidade);

            String mensagem = String.format("🚨 Estoque baixo: %s - %d unidades!", descricao, quantidade);
            pushNotificationService.enviarNotificacao("Estoque Baixo", mensagem);

            log.info("📤 fila do consumer enviada com sucesso para produto '{}'", descricao);

        } catch (Exception e) {
            log.error("❌ Erro ao processar mensagem da fila '{}'", RabbitMQConfig.FILA_ESTOQUE_BAIXO, e);
        }
    }
}
