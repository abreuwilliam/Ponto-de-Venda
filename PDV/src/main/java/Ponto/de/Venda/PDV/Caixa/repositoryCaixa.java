package Ponto.de.Venda.PDV.Caixa;


import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface repositoryCaixa  extends CrudRepository<ModelEstoque, Integer> {
   List<ModelEstoque> findAll();
   ModelEstoque findBycodigoProduto(Long codigoproduto);
   ModelEstoque save(ModelEstoque produtos);

}
