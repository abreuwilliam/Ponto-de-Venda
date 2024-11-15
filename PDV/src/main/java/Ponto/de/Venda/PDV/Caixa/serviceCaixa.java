package Ponto.de.Venda.PDV.Caixa;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class serviceCaixa {
    
    @Autowired
    private   repositoryCaixa repository;
   
   
    public   List<ModelEstoque> exibirTodosProdutos(){
    return repository.findAll();
    }
    public ModelEstoque pesquisarcodigoProduto(Long produto){
      return repository.findBycodigoProduto(produto);
    }
    public ModelEstoque salvarEstoque(ModelEstoque produto){
      return repository.save(produto);
    }
  }