package Ponto.de.Venda.PDV.pedidos;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@CrossOrigin(origins = "*") 
@RequestMapping(value = "/pedidos")
public class TelaPedidosController {

  @Autowired
   private Ponto.de.Venda.PDV.pedidos.cadastrarPedidos cadastraPedidos;

	@Autowired
  private Ponto.de.Venda.PDV.pedidos.servicePedidos servicePedidos;

	
   @Autowired
   private Ponto.de.Venda.PDV.pedidos.pedidos pedidos;

   @PostMapping
	public String postMethod(@RequestBody pedidos document) {
    return cadastraPedidos.CadastrarPedido(document);
    
	}

  @GetMapping
  public List<ModelPedidos> getpedidos() {
      return servicePedidos.exibirTodosProdutos(); 
  }

  @DeleteMapping
  public String deleteTodosPedidos() {
    servicePedidos.deletarTodosPedidos();
      return "Todos os pedidos foram excluídos com sucesso.";

	}
}