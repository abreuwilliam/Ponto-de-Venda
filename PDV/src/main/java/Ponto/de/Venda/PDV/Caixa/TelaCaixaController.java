package Ponto.de.Venda.PDV.Caixa;
import java.security.Provider.Service;
import java.util.List;
import java.util.Map;

import org.apache.naming.ServiceRef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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


	@GetMapping("/{codigoProduto}")
    public ResponseEntity<ModelEstoque> buscarProdutoPorCodigo(@PathVariable Long codigoProduto) {
        ModelEstoque produto = serviceCaixa.pesquisarcodigoProduto(codigoProduto);
        
        if (produto != null) {
            return ResponseEntity.ok(produto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Retorna 404 se o produto não for encontrado
        }
    }


	@PostMapping
	public String  postMethod(@RequestBody String document) {

		String codigoProdutoString = document.replaceAll("[^0-9]", "");
        Long codigoProduto = Long.parseLong(codigoProdutoString);
		caixa.setCodigo_Produto(codigoProduto);
		return  caixa.processamentoCaixa();
		
	}
}
