package com.pdv.papelaria;

import com.pdv.papelaria.repository.UsuarioRepository;
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

@SpringBootApplication
@EnableAspectJAutoProxy
@OpenAPIDefinition(info = @Info(title = "API", version = "1.0"))
@EnableCaching
public class PdvApplication {

	public static void main(String[] args) {
		System.out.println(">>> Iniciando aplicação PDV...");
		SpringApplication.run(PdvApplication.class, args);
		System.out.println(">>> Aplicação PDV iniciada com sucesso.");
	}

	@EventListener(ApplicationReadyEvent.class)
	public void onApplicationReady() {
		System.out.println(">>> Aplicação está pronta para receber requisições.");
	}

	@Bean
	public CommandLineRunner verificarUsuarios(UsuarioRepository usuarioRepository) {
		return args -> {
			System.out.println(">>> Verificando se os usuários foram carregados...");
			usuarioRepository.findAll().forEach(u ->
					System.out.println("Usuário carregado: " + u.getUsername() + " | Role: " + u.getRole()));
		};
	}
}
