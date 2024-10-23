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
    
    public Map<String, Object> processamentoCaixa() {
    // Obtém o produto do serviço
    ModelEstoque produto = servicecaixa.pesquisarcodigoProduto(codigo_Produto);

    // Cria um mapa para armazenar apenas os campos desejados
    Map<String, Object> response = new HashMap<>();
    response.put("produto", produto.getProduto());
    response.put("preco", produto.getPreco());

    // Retorna o mapa
    return response;
}
}
