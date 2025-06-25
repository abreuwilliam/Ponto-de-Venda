package com.pdv.papelaria.dto;

import com.pdv.papelaria.entities.Pedidos;

public class PedidosDto {
    private String pedidos;

    public PedidosDto(Pedidos entity) {
        this.pedidos = entity.getpedidos();
    }

    public PedidosDto() {
    }

    public String getPedidos() {
        return pedidos;
    }

    public void setPedidos(String pedidos) {
        this.pedidos = pedidos;
    }
}
