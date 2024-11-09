package Ponto.de.Venda.PDV.baixaEstoque;

    import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface repositoryBaixa  extends CrudRepository<ModelBaixa, Integer> {
   List<ModelBaixa> findAll();
   ModelBaixa findByProduto(String codigoproduto);
   ModelBaixa save(ModelBaixa produtos);
}
