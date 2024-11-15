package Ponto.de.Venda.PDV.alterarProduto;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class Alterar {

    @Autowired
    private repositoryAlterar repositoryAlterar;

    @Autowired
    private produtosAlterar produtosAlterar;

    public Long Alterar(produtosAlterar produtosAlterar){
        this.produtosAlterar = produtosAlterar;

        try {
          
            ModelAlterar estoque = repositoryAlterar.findBycodigoProduto(produtosAlterar.getCodigoProduto());
            
            if (estoque != null) {
                estoque.setCodigoProduto(produtosAlterar.getCodigoProduto());
                estoque.setPreco(produtosAlterar.getPreco());
                estoque.setProduto(produtosAlterar.getProduto());
                estoque.setQuantidadeEstoque(produtosAlterar.getQuantidadeEstoque());
                
                
                repositoryAlterar.save(estoque);
                return estoque.getCodigoProduto();
            } else {
               return 4l;
            }

        } catch (Exception e) {
            e.printStackTrace();
            
            return 5l;
        }
    }
}
