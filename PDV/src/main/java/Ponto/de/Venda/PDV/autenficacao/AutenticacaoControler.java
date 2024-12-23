package Ponto.de.Venda.PDV.autenficacao;

import Ponto.de.Venda.PDV.autenficacao.AuthenticationDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class AutenticacaoControler {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenServices tokenService;
    @PostMapping
    public ResponseEntity<?> login(@RequestBody @Valid AuthenticationDTO data) {
        try {
            System.out.println("Recebendo requisição de login para: " + data.usuario());
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.usuario(), data.senha());
            var user = authenticationManager.authenticate(usernamePassword);
    
            System.out.println("Usuário autenticado com sucesso: " + user.getName());
    
            // Gera o token com a role
            var token = tokenService.generateToken((UserDetails) user.getPrincipal());
    
            // A role já está embutida no token, então não é necessário extraí-la separadamente
            var role = ((UserDetails) user.getPrincipal()).getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .findFirst()
                        .orElse("ROLE_USER"); // Role padrão caso não encontre
    
            return ResponseEntity.ok(new LoginRespondeDTO(token, role));
        } catch (Exception e) {
            System.err.println("Erro durante a autenticação: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
        }
    }
}    