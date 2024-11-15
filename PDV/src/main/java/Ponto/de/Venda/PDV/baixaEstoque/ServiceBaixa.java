package Ponto.de.Venda.PDV.baixaEstoque;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import Ponto.de.Venda.PDV.Caixa.repositoryCaixa;

@Service
public class ServiceBaixa {
    
    @Autowired
    private   repositoryBaixa repository;
   
   
    public   List<ModelBaixa> exibirTodosProdutos(){
    return repository.findAll();
    }
    public ModelBaixa pesquisarcodigoProduto(String produto){
      return repository.findByProduto(produto);
    }
    public ModelBaixa salvarEstoque(ModelBaixa produto){
      return repository.save(produto);
    }
  }
