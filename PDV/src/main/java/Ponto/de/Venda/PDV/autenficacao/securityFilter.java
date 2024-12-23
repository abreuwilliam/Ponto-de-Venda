package Ponto.de.Venda.PDV.autenficacao;

import java.io.IOException;
import java.security.Provider.Service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



@Component
public class securityFilter extends OncePerRequestFilter {

    @Autowired
    TokenServices tokenServices;

    @Autowired
    repository repository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
                var token =this.recoverToken(request);
                if(token != null){
                    var login = tokenServices.validateToken(token);
                    modelLogin user = repository.findByusuario(login);
                    var autenficacao = new UsernamePasswordAuthenticationToken(user,null, user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(autenficacao);
                }
                filterChain.doFilter(request, response);
              
    
    }
    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization"); // Corrigido para 'Authorization'
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        return authHeader.substring(7); // Retorna o token após "Bearer "
    }
}
