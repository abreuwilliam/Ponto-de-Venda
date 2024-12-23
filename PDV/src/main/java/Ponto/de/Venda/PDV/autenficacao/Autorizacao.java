package Ponto.de.Venda.PDV.autenficacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class Autorizacao implements UserDetailsService {

    @Autowired
    repository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = repository.findByusuario(username);
        if (user == null) {
            System.out.println("Usuário não encontrado no banco: " + username);
            throw new UsernameNotFoundException("Usuário não encontrado");
        }
        System.out.println("Usuário encontrado: " + user.getUsername());
        return user;
    }
    

    
}
