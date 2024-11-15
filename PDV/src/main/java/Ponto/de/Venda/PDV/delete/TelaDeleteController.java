package Ponto.de.Venda.PDV.delete;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.transaction.Transactional;
@Transactional
@RestController
@CrossOrigin(origins = "*")  // Permite requisições de qualquer origem
@RequestMapping(value = "/delete")
public class TelaDeleteController {



	@Autowired
   private ServiceDelete serviceDelete;

   @Autowired
   private repositoryDelete repository;

   @Autowired
   private CodigoProdutoDTO CodigoProdutoDTO;
   
@DeleteMapping("/{codigoProduto}")
    public String buscarProdutoPorCodigo(@PathVariable Long codigoProduto) {
        serviceDelete.deletaEstoque(codigoProduto);
        return "deletado com sucesso";
    }

   @PostMapping
public String postMethod(@RequestBody String produto) {
    try {
		ModelDelete modelProduto = repository.findByproduto(produto);
if (modelProduto != null) {
    repository.delete(repository.findByproduto(produto));
    return "Produto excluído com sucesso!";
} else {
    return "Produto não encontrado!";
}

    } catch (Exception e) {
        e.printStackTrace();  
        return "Erro: " + e.getMessage();
    }
}

}
