package Ponto.de.Venda.PDV.ConsultaProduto;


import java.util.List;

import org.springframework.data.repository.CrudRepository;



public interface repositoryConsulta  extends CrudRepository<ModelEstoqueConsulta, Integer> {
   List<ModelEstoqueConsulta> findAll();
   ModelEstoqueConsulta findByProduto(String codigoproduto);
   ModelEstoqueConsulta save(ModelEstoqueConsulta produtos);
   List<ModelEstoqueConsulta> findByProdutoStartingWith(String produto);
}
