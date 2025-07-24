package com.pdv.papelaria.dto;

import com.pdv.papelaria.entities.Pedidos;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidosDto {
    private String pedidos;

    public PedidosDto(Pedidos entity) {
        this.pedidos = entity.getPedidos();
    }
}
