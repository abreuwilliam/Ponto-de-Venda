package Ponto.de.Venda.PDV.autenficacao;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceLogin {
    
    @Autowired
    private   repository repository;
    public String exibirTodosProdutos;
   
    public   List<modelLogin> exibirTodosProdutos(){
    return repository.findAll();
    //List<ModelEstoque>  produto = (List<ModelEstoque>) 
    // produto.forEach(produtos -> System.out.println(produtos));
    }

    public boolean pesquisarusuario(String usuario){
      if (repository.findByusuario(usuario).equals(null)) {
        return false;
      }else{
        return true;}
      }
      public boolean pesquisarsenha(String senha){
        if (repository.findBysenha(senha).equals(null)) {
          return false;
        }else{
          return true;
        }

    }
    }