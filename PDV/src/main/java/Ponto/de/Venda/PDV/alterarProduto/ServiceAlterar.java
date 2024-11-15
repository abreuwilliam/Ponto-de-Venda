package Ponto.de.Venda.PDV.alterarProduto;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceAlterar {
    
    @Autowired
    private   repositoryAlterar repository;
   
    public   List<ModelAlterar> exibirTodosProdutos(){
    return repository.findAll();
    }
    public ModelAlterar pesquisarcodigoProduto(Long produto){
      return repository.findBycodigoProduto(produto);
    }
    public ModelAlterar salvarEstoque(ModelAlterar produto){
      return repository.save(produto);
    }
  }
