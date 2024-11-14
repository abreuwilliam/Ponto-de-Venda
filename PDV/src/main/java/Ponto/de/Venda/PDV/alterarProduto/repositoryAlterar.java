package Ponto.de.Venda.PDV.alterarProduto;


    import java.util.List;

import org.springframework.data.repository.CrudRepository;

import Ponto.de.Venda.PDV.alterarProduto.ModelAlterar;

public interface repositoryAlterar  extends CrudRepository<ModelAlterar, Integer> {
   List<ModelAlterar> findAll();
   ModelAlterar findBycodigoProduto(Long codigoproduto);
   ModelAlterar save(ModelAlterar estoque);
}

