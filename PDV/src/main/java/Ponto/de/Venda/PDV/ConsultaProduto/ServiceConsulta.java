package Ponto.de.Venda.PDV.ConsultaProduto;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import Ponto.de.Venda.PDV.ConsultaProduto.ModelEstoqueConsulta;

@Service
public class ServiceConsulta {
    
    @Autowired
    private   repositoryConsulta repository;
   
   
    public   List<ModelEstoqueConsulta> exibirTodosProdutos(){
    return repository.findAll();
    
    }

    public ModelEstoqueConsulta pesquisarProduto(String produto){
      return repository.findByProduto(produto);
    }
    public ModelEstoqueConsulta salvarEstoque(ModelEstoqueConsulta produto){
      return repository.save(produto);
    }
    public List<ModelEstoqueConsulta> pesquisarProdutoLike(String produto) {
      return repository.findByProdutoStartingWith(produto);
  }
  
  }