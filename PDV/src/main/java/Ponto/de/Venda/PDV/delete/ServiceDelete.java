package Ponto.de.Venda.PDV.delete;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Ponto.de.Venda.PDV.Caixa.repositoryCaixa;

@Service
public class ServiceDelete {
    
    @Autowired
    private   repositoryDelete  repository;
   
   
    public   List<ModelDelete> exibirTodosProdutos(){
    return repository.findAll();
    ;
    }

    public ModelDelete pesquisarcodigoProduto(String produto){
      return repository.findByProduto(produto);
    }
    public ModelDelete salvarEstoque(ModelDelete produto){
      return repository.save(produto);
    }
  }
