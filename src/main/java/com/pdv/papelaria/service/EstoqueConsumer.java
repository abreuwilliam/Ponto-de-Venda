package com.pdv.papelaria.service;

import com.pdv.papelaria.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class EstoqueConsumer {

    @Autowired
    private EmailService emailService;

    @RabbitListener(queues = RabbitMQConfig.FILA_ESTOQUE_BAIXO)
    public void receber(Map<String, Object> payload) {
        String descricao = (String) payload.get("descricao");
        Integer quantidade = (Integer) payload.get("quantidade");

        String assunto = "Estoque Baixo: " + descricao;
        String corpo = "Atenção! O produto '" + descricao + "' está com apenas " + quantidade + " unidades no estoque.";

        emailService.enviar("seuemail@dominio.com", assunto, quantidade);
    }
}
