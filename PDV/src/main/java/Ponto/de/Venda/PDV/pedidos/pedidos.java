package Ponto.de.Venda.PDV.pedidos;

import org.springframework.stereotype.Component;

@Component
public class pedidos {
    private String pedidos;

    public String getpedidos() {
        return pedidos;
    }

    public void setpedidos(String pedidos) {
        this.pedidos = pedidos;
    }
}
