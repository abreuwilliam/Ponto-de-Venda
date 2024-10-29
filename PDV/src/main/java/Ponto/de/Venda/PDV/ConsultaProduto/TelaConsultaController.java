package Ponto.de.Venda.PDV.ConsultaProduto;
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
@RequestMapping(value = "/consulta")
public class TelaConsultaController {

  @Autowired
  private consulta consulta;
   @PostMapping
	public List<ModelEstoqueConsulta> postMethod(@RequestBody String document) {
   return consulta.realizarConsulta(document);
  
	}

	}

 