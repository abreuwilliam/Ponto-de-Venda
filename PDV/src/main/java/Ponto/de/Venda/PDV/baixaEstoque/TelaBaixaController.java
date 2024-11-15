package Ponto.de.Venda.PDV.baixaEstoque;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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
        int quantidade = (int) produto.get("quantidade");
  
        ModelBaixa modelProduto = repositoryBaixa.findByProduto(descricao);
        if (modelProduto != null) {
            modelProduto.setQuantidadeEstoque(modelProduto.getQuantidadeEstoque() - quantidade); 
            repositoryBaixa.save(modelProduto); 
        }
    }
    return "Estoque atualizado com sucesso!";
}
}