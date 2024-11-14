package Ponto.de.Venda.PDV.alterarProduto;

import org.springframework.stereotype.Component;
import Ponto.de.Venda.PDV.Caixa.ModelEstoque;
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
            // Buscar produto pelo nome (Produto)
            ModelAlterar estoque = repositoryAlterar.findBycodigoProduto(produtosAlterar.getCodigoProduto());
            
            if (estoque != null) {
                // Produto encontrado, realizar a atualização
                estoque.setCodigoProduto(produtosAlterar.getCodigoProduto());
                estoque.setPreco(produtosAlterar.getPreco());
                estoque.setProduto(produtosAlterar.getProduto());
                estoque.setQuantidadeEstoque(produtosAlterar.getQuantidadeEstoque());
                
                // Salvar as alterações
                repositoryAlterar.save(estoque);
                //return "alterado com sucesso";
                return estoque.getCodigoProduto();
            } else {
                // Produto não encontrado, retornar mensagem de erro
               // return "Produto não encontrado para atualização";
               return 4l;
            }

        } catch (Exception e) {
            // Log do erro e retorno de mensagem amigável
            e.printStackTrace();
            //return "Erro ao atualizar produto: " + e.getMessage();
            return 5l;
        }
    }
}
