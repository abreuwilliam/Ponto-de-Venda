package Ponto.de.Venda.PDV.delete;


    import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface repositoryDelete  extends CrudRepository<ModelDelete, Integer> {
   List<ModelDelete> findAll();
   ModelDelete findByproduto(String produto);
   ModelDelete save(ModelDelete produtos);
   void deleteBycodigoProduto(Long codigoProduto);
}
