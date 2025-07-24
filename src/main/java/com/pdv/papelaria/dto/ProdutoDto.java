package com.pdv.papelaria.dto;

import com.pdv.papelaria.entities.Produto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDto {
    private Long codigoProduto;
    private String produto;
    private double preco;
    private int quantidadeEstoque;

    public ProdutoDto(Produto entity) {
        this.codigoProduto = entity.getCodigoProduto();
        this.produto = entity.getProduto();
        this.preco = entity.getPreco();
        this.quantidadeEstoque = entity.getQuantidadeEstoque();
    }
}
