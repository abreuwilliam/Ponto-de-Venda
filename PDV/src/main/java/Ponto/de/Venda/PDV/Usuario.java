package Ponto.de.Venda.PDV;

import org.springframework.stereotype.Component;

@Component
public class Usuario {
    private String usuario;
    private String senha;

    // Getters e Setters
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
