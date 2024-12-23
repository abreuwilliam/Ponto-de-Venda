package Ponto.de.Venda.PDV.autenficacao;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

@Service
public class TokenServices {

    @Value("${api.security.token.secret}")
    private String secreat;

    private Instant genExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secreat);
            return JWT.require(algorithm)
                    .withIssuer("pdv")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTCreationException exception) {
            return ""; // Retorna string vazia em caso de erro
        }
    }

   public String generateToken(UserDetails user) {
    try {
        Algorithm algorithm = Algorithm.HMAC256(secreat);
        return JWT.create()
                .withIssuer("pdv")
                .withSubject(user.getUsername())
                .withExpiresAt(genExpirationDate())
                .withClaim("role", user.getAuthorities().stream()
                                     .map(GrantedAuthority::getAuthority)
                                     .findFirst()
                                     .orElse("ROLE_USER")) // Adiciona a role no token
                .sign(algorithm);
    } catch (JWTCreationException exception) {
        throw new RuntimeException("Erro ao gerar token");
    }
}

public String extractRoleFromToken(String token) {
    try {
        Algorithm algorithm = Algorithm.HMAC256(secreat);
        return JWT.require(algorithm)
                  .withIssuer("pdv")
                  .build()
                  .verify(token)
                  .getClaim("role")  // Recupera a role do token
                  .asString();
    } catch (JWTCreationException exception) {
        throw new RuntimeException("Erro ao extrair role do token");
    }
}

    
}
