package Ponto.de.Venda.PDV.autenficacao;


import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface repository  extends CrudRepository<modelLogin, Integer> {
   List<modelLogin> findAll();
   modelLogin findByusuario(String usuario);
   modelLogin findBysenha(String senha); 
   modelLogin save(modelLogin usuario);

}
