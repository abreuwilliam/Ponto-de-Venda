package Ponto.de.Venda.PDV.autenficacao;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "login")
public class modelLogin implements UserDetails{ 

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "usuario")
    private String usuario; 

    @Column(name = "Role")
    @Enumerated(EnumType.STRING)
    private userRoles role; 

    @Column(name = "senha")
    private String senha;

   
    // Getters e setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getusuario() {
        return usuario;
    }

    public void setusuario(String usuario) {
        this.usuario = usuario;
    }

    
    public String getsenha() {
        return senha;
    }

    public void setsenha(String senha) {
        this.senha = senha;
    }

    public userRoles getrole() {
        return role;
    }
    
    public void setrole(userRoles role) {
        this.role = role;
    }
    

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
    if (this.role == role.ADMIN) {
        return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));  // Apenas ROLE_ADMIN para admins
    } else {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));  // Apenas ROLE_USER para usuários comuns
    }
}

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return usuario;
    }
}
