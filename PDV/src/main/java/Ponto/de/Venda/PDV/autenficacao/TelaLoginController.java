/*package Ponto.de.Venda.PDV.autenficacao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@CrossOrigin(origins = "*")  // Permite requisições de qualquer origem
@RequestMapping(value = "/use")
public class TelaLoginController {

	@Autowired
   private ServiceLogin serviceLogin;

   @Autowired
   private  Autenticacao autenticacao;;

   @Autowired
   private Usuario usuario;

	@GetMapping
	public List<modelLogin> home() {
		List<modelLogin> usuario = (List<modelLogin>) serviceLogin.exibirTodosProdutos() ;
	    usuario.forEach(usuarios -> System.out.println(usuarios));
		return usuario;
	}

	@PostMapping
	public String postMethod(@RequestBody String document) {
		usuario.setUsuario(document);
		return autenticacao.autentificacao();
	}
}
*/