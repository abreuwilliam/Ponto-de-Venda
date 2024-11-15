package Ponto.de.Venda.PDV.Caixa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@CrossOrigin(origins = "*")  // Permite requisições de qualquer origem
@RequestMapping(value = "/cadastro")
public class TelaCadastroController {

	@Autowired
  private Ponto.de.Venda.PDV.Caixa.serviceCaixa serviceCaixa;

   @Autowired
   private Ponto.de.Venda.PDV.Caixa.cadastra cadastra;

   @PostMapping
	public String postMethod(@RequestBody produtos document) {
    return cadastra.cadastrar(document);
	}
	}

 