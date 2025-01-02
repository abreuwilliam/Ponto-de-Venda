package Ponto.de.Venda.PDV.autenficacao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.stereotype.Component;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;


@Component
public class securityFilter extends OncePerRequestFilter {


    @Autowired
    private TokenServices tokenServices;


    @Autowired
    private repository repository;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {


        String token = recoverToken(request);
       
        if (token != null) {
            String login = tokenServices.validateToken(token); // Validando o token
            String role = tokenServices.extractRoleFromToken(token); // Extraindo a role
           
            // Buscando o usuário no repositório
            modelLogin user = repository.findByusuario(login);
           
            if (user != null) {
                // Criando o token de autenticação com as autoridades do usuário
                UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
               
                // Estabelecendo o contexto de segurança
                SecurityContextHolder.getContext().setAuthentication(authentication);
               
                // Verificação de permissão para a rota "alterar"
                if (request.getRequestURI().contains("/alterar") && !role.equals("ROLE_ADMIN")) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN); // Acesso negado
                    response.getWriter().write("Acesso negado. Você não tem permissão para alterar.");
                    return;
                }


                System.out.println("Token recebido: " + token);
                System.out.println("Role atribuída: " + role);
            } else {
                System.out.println("Usuário não encontrado: " + login);
            }
        }
       
        // Continua o processamento da requisição
        filterChain.doFilter(request, response);
    }


    // Método para recuperar o token do header Authorization
    private String recoverToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
       
        // Verificando se o header está presente e começa com "Bearer "
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7); // Retorna o token
        }
       
        return null; // Retorna null caso o token não esteja presente ou seja inválido
    }
}


