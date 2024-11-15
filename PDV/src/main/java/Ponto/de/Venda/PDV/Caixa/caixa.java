package Ponto.de.Venda.PDV.Caixa;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;




@Component
public class caixa {

    @Autowired
    private serviceCaixa servicecaixa;
    
    private Long codigo_Produto;

    public Long getCodigo_Produto() {
        return codigo_Produto;
    }

    public void setCodigo_Produto(Long codigo_Produto) {
        this.codigo_Produto = codigo_Produto;
    }
    
    public String processamentoCaixa() {
    try {  
    ModelEstoque produto = servicecaixa.pesquisarcodigoProduto(codigo_Produto);
    return produto.getProduto() + " " + produto.getPreco();
    
} catch (Exception e) {
    e.printStackTrace();
       e.printStackTrace();
            return "produto nao encontrado";
}
}
}
