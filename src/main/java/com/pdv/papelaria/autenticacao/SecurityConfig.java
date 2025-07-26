package com.pdv.papelaria.autenticacao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtFilter) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))
                .authorizeHttpRequests(auth -> auth
                        // Rotas públicas
                        .requestMatchers(
                                "/auth/login",
                                "/auth/**",
                                "/h2-console/**",
                                "/actuator/prometheus",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/swagger-resources/**",
                                "/webjars/**"
                        ).permitAll()

                        .requestMatchers(HttpMethod.POST, "/api/subscriptions").permitAll()

                        // Liberar rotas específicas
                        .requestMatchers(HttpMethod.POST, "/produto/consulta").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/produto/codigo/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, "/produto/caixa").hasAnyRole("ADMIN", "USER") // <-- ADICIONE ESTA LINHA

                        // Rotas exclusivas para ADMIN
                        .requestMatchers("/alterar/**", "/cadastrar/**", "/deletar/**", "/api/admin/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/produto/**").hasRole("ADMIN") // exceto as liberadas acima
                        .requestMatchers(HttpMethod.PUT, "/produto/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/produto/**").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UsuarioDetailsService();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(List.of(authProvider));
    }
}
