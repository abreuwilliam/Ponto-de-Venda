package com.pdv.papelaria;

import com.pdv.papelaria.repository.UsuarioRepository;
import com.pdv.papelaria.entities.Usuario;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
@EnableAspectJAutoProxy
@OpenAPIDefinition(info = @Info(title = "API", version = "1.0"))
@EnableCaching
public class PdvApplication {

	private static final Logger logger = LoggerFactory.getLogger(PdvApplication.class);

	public static void main(String[] args) {
		logger.info(">>> Iniciando aplicação PDV...");
		long inicio = System.currentTimeMillis();

		SpringApplication.run(PdvApplication.class, args);

		long fim = System.currentTimeMillis();
		logger.info(">>> Aplicação PDV iniciada com sucesso.");
		logger.info(">>> Tempo total de inicialização: {} ms", (fim - inicio));
	}

	@EventListener(ApplicationReadyEvent.class)
	public void onApplicationReady() {
		logger.info(">>> Aplicação está pronta para receber requisições.");
	}

	@Bean
	public CommandLineRunner verificarUsuarios(UsuarioRepository usuarioRepository) {
		return args -> {
			logger.info(">>> Verificando se os usuários foram carregados do banco...");
			try {
				long total = usuarioRepository.count();
				logger.info(">>> Total de usuários encontrados: {}", total);

				for (Usuario u : usuarioRepository.findAll()) {
					logger.info(">>> Usuário carregado: {} | Role: {}", u.getUsername(), u.getRole());
				}
			} catch (Exception e) {
				logger.error("!!! ERRO ao acessar o banco de dados ou listar usuários", e);
			}
		};
	}
}
