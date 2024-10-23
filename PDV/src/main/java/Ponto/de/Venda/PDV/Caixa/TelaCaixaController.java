package Ponto.de.Venda.PDV.Caixa;
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
@RequestMapping(value = "/caixa")
public class TelaCaixaController {



	@Autowired
   private Ponto.de.Venda.PDV.Caixa.serviceCaixa serviceCaixa;
	
   @Autowired
   private Ponto.de.Venda.PDV.Caixa.caixa caixa;


	@GetMapping
	public ModelEstoque home() {
		List<ModelEstoque> produto = (List<ModelEstoque>) serviceCaixa.exibirTodosProdutos() ;
	    produto.forEach(produtos -> System.out.println(produtos));
		//return produto;
      return (ModelEstoque) serviceCaixa.pesquisarcodigoProduto(22225235565L);
	}

	@PostMapping
	public Map<String, Object> postMethod(@RequestBody produtos document) {

		caixa.setCodigo_Produto(document.getCodigo_Produto());
		return  caixa.processamentoCaixa();
		
	}
	
}
