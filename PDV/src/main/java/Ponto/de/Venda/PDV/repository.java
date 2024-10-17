package Ponto.de.Venda.PDV;


import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface repository  extends CrudRepository<ModelEstoque, Integer> {
   List<ModelEstoque> findAll();
   ModelEstoque findByproduto(String produto); 
   ModelEstoque save(ModelEstoque produto);

}
