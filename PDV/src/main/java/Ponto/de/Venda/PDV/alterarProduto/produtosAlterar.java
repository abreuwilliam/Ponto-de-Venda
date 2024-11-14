package Ponto.de.Venda.PDV.alterarProduto;


import org.springframework.stereotype.Component;

@Component
public class produtosAlterar {
    private String produto;
    private long codigoProduto;
    private double preco;
    private int quantidadeEstoque;
    

    public String getProduto() {
        return produto;
    }
    public void setProduto(String produto) {
        this.produto = produto;
    }
    public long getCodigoProduto() {
        return codigoProduto;
    }
    public void setCodigoProduto(long CodigoProduto) {
        this.codigoProduto = CodigoProduto;
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

