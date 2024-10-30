package Ponto.de.Venda.PDV.pedidos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component
public class cadastrarPedidos {

@Autowired    
private pedidos pedidos;

 @Autowired
private servicePedidos servicePedidos;

    public String CadastrarPedido(pedidos pedidos){
        try {
            ModelPedidos modelpedidos = new ModelPedidos();
        modelpedidos.setPedidos(pedidos.getpedidos());
        servicePedidos.salvarEstoque(modelpedidos);
            return "pedido cadastrado";
        } catch (Exception e) {
            e.printStackTrace();
            e.printStackTrace();
                 return "pedido nao cadastrado";
        }
        
    }
}
