package com.pdv.papelaria.dto;

import com.pdv.papelaria.entities.Produto;

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
    public ProdutoDto() {
    }



    public Long getCodigoProduto() {
        return codigoProduto;
    }

    public void setCodigoProduto(Long codigoProduto) {
        this.codigoProduto = codigoProduto;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(int quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }
}
