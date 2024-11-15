package Ponto.de.Venda.PDV.delete;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceDelete {
    
    @Autowired
    private   repositoryDelete  repository;
   
   
    public   List<ModelDelete> exibirTodosProdutos(){
    return repository.findAll();
    }
    public ModelDelete pesquisarcodigoProduto(String produto){
      return repository.findByproduto(produto);
    }
    public ModelDelete salvarEstoque(ModelDelete produto){
      return repository.save(produto);
    }
    public void deletaEstoque(Long codigoProduto){
       repository.deleteBycodigoProduto(codigoProduto);
    }
  }
