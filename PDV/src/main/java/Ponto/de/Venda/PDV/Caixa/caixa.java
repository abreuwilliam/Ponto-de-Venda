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
    // Obtém o produto do serviço
    try {
        
    ModelEstoque produto = servicecaixa.pesquisarcodigoProduto(codigo_Produto);

    return produto.getProduto() + " " + produto.getPreco();
    // Cria um mapa para armazenar apenas os campos desejados
    

    // Retorna o mapa
    
} catch (Exception e) {
    e.printStackTrace();
       e.printStackTrace();
            return "produto nao encontrado";
}
}
}
