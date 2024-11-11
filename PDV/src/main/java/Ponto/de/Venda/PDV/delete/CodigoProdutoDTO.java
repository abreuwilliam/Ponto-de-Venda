package Ponto.de.Venda.PDV.delete;

import org.springframework.stereotype.Component;

@Component
public class CodigoProdutoDTO {
    private String codigoProduto;

    public String getCodigoProduto() {
        return codigoProduto;
    }

    public void setCodigoProduto(String codigoProduto) {
        this.codigoProduto = codigoProduto;
    }
}

