package Ponto.de.Venda.PDV;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")  // Permite requisições de qualquer origem
@RequestMapping(value = "/")
public class TelaLoginController {

	@GetMapping
	public String home() {
		String documento = "deu certo";
		return documento;
	}
}
