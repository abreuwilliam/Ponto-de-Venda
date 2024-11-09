package Ponto.de.Venda.PDV.delete;

package Ponto.de.Venda.PDV.autenficacao;
import java.security.Provider.Service;
import java.util.List;

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
@RequestMapping(value = "/user")
public class TelaDeleteController {



	@Autowired
   private ServiceDelete serviceDelete;

   

	@PostMapping
	public String postMethod(@RequestBody String document) {
		return document;
		
		
	}
	
}