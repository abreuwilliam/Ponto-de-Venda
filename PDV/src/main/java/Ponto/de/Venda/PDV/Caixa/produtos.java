package Ponto.de.Venda.PDV.Caixa;

import org.springframework.stereotype.Component;

@Component
public class produtos {
    private String produto;
    private long codigo_Produto;
    private double preco;
    private int quantidade_Estoque;
    

    public String getProduto() {
        return produto;
    }
    public void setProduto(String produto) {
        this.produto = produto;
    }
    public long getCodigo_Produto() {
        return codigo_Produto;
    }
    public void setCodigo_Produto(long codigo_Produto) {
        this.codigo_Produto = codigo_Produto;
    }
    public double getPreco() {
        return preco;
    }
    public void setPreco(double preco) {
        this.preco = preco;
    }
    public int getQuantidade_Estoque() {
        return quantidade_Estoque;
    }
    public void setQuantidade_Estoque(int quantidade_Estoque) {
        this.quantidade_Estoque = quantidade_Estoque;
    }
}
