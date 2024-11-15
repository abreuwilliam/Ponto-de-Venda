package Ponto.de.Venda.PDV.pedidos;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface repositorypedidos  extends CrudRepository<ModelPedidos, Integer> {

   List<ModelPedidos> findAll();
   ModelPedidos findBypedidos(String pedidos);
   ModelPedidos save(ModelPedidos pedidos);

}
