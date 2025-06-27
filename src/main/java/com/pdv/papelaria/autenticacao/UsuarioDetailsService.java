package com.pdv.papelaria.autenticacao;

import com.pdv.papelaria.entities.Usuario;
import com.pdv.papelaria.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class UsuarioDetailsService implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(UsuarioDetailsService.class);

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Tentando autenticar usuário: {}", username);

        Usuario usuario = usuarioRepository.findByUsername(username);
        if (usuario == null) {
            log.warn("Usuário não encontrado no banco de dados: {}", username);
            throw new UsernameNotFoundException("Usuário não encontrado");
        }

        log.info("Usuário encontrado: {} com role: {}", usuario.getUsername(), usuario.getRole());

        return new org.springframework.security.core.userdetails.User(
                usuario.getUsername(),
                usuario.getPassword(), // Certifique-se de que está criptografada
                getPermissoes(usuario)
        );
    }

    private Collection<? extends GrantedAuthority> getPermissoes(Usuario usuario) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        String roleFormatada = "ROLE_" + usuario.getRole();
        log.debug("Adicionando permissão: {}", roleFormatada);
        authorities.add(new SimpleGrantedAuthority(roleFormatada));
        return authorities;
    }
}
