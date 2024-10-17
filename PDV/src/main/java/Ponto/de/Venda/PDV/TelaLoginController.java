package Ponto.de.Venda.PDV;
import java.security.Provider.Service;
import java.util.List;

import org.apache.naming.ServiceRef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import Ponto.de.Venda.PDV.*;
import Ponto.de.Venda.PDV.modelEstoque.ModelEstoque;
@RestController
@CrossOrigin(origins = "*")  // Permite requisições de qualquer origem
@RequestMapping(value = "/user")
public class TelaLoginController {



	@Autowired
   private ServiceEstoque serviceestoque;

	@GetMapping
	public List<ModelEstoque> home() {
		//serviceestoque.exibirTodosProdutos();
		List<ModelEstoque> produto = (List<ModelEstoque>) serviceestoque.exibirTodosProdutos() ;
	    produto.forEach(produtos -> System.out.println(produtos));
		return produto;
	}
}
