package Ponto.de.Venda.PDV.pedidos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class servicePedidos {
    
    @Autowired
    private   repositorypedidos repository;
   
   
    public   List<ModelPedidos> exibirTodosProdutos(){
    return repository.findAll();
   
    }

    public ModelPedidos pesquisarpedidos(String pedidos){
      return repository.findBypedidos(pedidos);
    }
    public ModelPedidos salvarEstoque(ModelPedidos pedidos){
      return repository.save(pedidos);
    }
    public void deletarTodosPedidos(){
      repository.deleteAll();
    }
  }
