package Ponto.de.Venda.PDV.baixaEstoque;

import java.security.Provider.Service;
import java.util.List;
import java.util.Map;

import org.apache.naming.ServiceRef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@CrossOrigin(origins = "*")  // Permite requisições de qualquer origem
@RequestMapping(value = "/baixa")
public class TelaBaixaController {



	@Autowired
  private ServiceBaixa serviceBaixa;

  @Autowired
	private repositoryBaixa repositoryBaixa;
   @Autowired
   private Ponto.de.Venda.PDV.Caixa.cadastra cadastra;

   @PostMapping
	 public String postMethod(@RequestBody List<Map<String, Object>> produtos) {
    for (Map<String, Object> produto : produtos) {
        String descricao = (String) produto.get("descricao");
        Double preco = (Double) produto.get("preco");
       
  
        // Busca o produto no banco de dados
        ModelBaixa modelProduto = repositoryBaixa.findByProduto(descricao);
        if (modelProduto != null) {
            // Atualiza a quantidade no estoque
            modelProduto.setQuantidadeEstoque(modelProduto.getQuantidadeEstoque() - 1); // Diminui 1 unidade
            repositoryBaixa.save(modelProduto); // Salva o produto atualizado
        }
    }
    return "Estoque atualizado com sucesso!";
}
}