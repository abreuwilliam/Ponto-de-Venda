package Ponto.de.Venda.PDV.autenficacao;

public enum userRoles{
    ADMIN("ADMIN"), 
    USER("USER");

    private String role;

    userRoles(String role){
        this.role = role;
    }
public String getRole() {
    return role;
}
}