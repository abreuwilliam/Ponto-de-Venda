package com.pdv.papelaria.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviar(String destinatario, String nomeProduto, int quantidadeAtual) {
        log.info("Iniciando envio de e-mail para {} sobre o produto '{}'", destinatario,nomeProduto);

        try {
            SimpleMailMessage mensagem = new SimpleMailMessage();
            mensagem.setTo(destinatario);
            mensagem.setSubject("Estoque Baixo - " );
            mensagem.setText("O produto '" + nomeProduto + "' está com estoque baixo. Quantidade atual: " + quantidadeAtual);

            mailSender.send(mensagem);
            log.info("E-mail enviado com sucesso para {}", destinatario);
        } catch (Exception e) {
            log.error("Erro ao enviar e-mail para {}: {}", destinatario, e.getMessage(), e);
        }
    }
}

