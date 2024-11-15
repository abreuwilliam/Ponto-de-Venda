package Ponto.de.Venda.PDV.ConsultaProduto;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@CrossOrigin(origins = "*") 
@RequestMapping(value = "/consulta")
public class TelaConsultaController {

  @Autowired
  private consulta consulta;
   @PostMapping
	public List<ModelEstoqueConsulta> postMethod(@RequestBody String document) {
   return consulta.realizarConsulta(document);
	}
	}

 