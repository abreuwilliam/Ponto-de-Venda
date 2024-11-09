package Ponto.de.Venda.PDV.delete;


    import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface repositoryDelete  extends CrudRepository<ModelDelete, Integer> {
   List<ModelDelete> findAll();
   ModelDelete findByProduto(String codigoproduto);
   ModelDelete save(ModelDelete produtos);
}
