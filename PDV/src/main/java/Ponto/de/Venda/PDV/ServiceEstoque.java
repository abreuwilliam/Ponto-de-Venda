package Ponto.de.Venda.PDV;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Ponto.de.Venda.PDV.modelEstoque.ModelEstoque;

@Service
public class ServiceEstoque {
    
    @Autowired
    private   repository repository;
    public String exibirTodosProdutos;
   
    public   List<ModelEstoque> exibirTodosProdutos(){
    return repository.findAll();
    //List<ModelEstoque>  produto = (List<ModelEstoque>) 
    // produto.forEach(produtos -> System.out.println(produtos));
    }

    public ModelEstoque pesquisarproduto(String produto){
      return repository.findByproduto(produto);
    }
    }