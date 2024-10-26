package Ponto.de.Venda.PDV.Caixa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class cadastra {
    
    @Autowired
    private serviceCaixa serviceCaixa;

    @Autowired
    private produtos produtos;

    public String cadastrar(produtos produtos){
        this.produtos = produtos;
       
        try {
        ModelEstoque estoque = new ModelEstoque();

        estoque.setCodigoProduto(produtos.getCodigo_Produto());
        estoque.setProduto(produtos.getProduto());
        estoque.setPreco(produtos.getPreco());
        estoque.setQuantidadeEstoque(produtos.getQuantidade_Estoque
        ());

        serviceCaixa.salvarEstoque(estoque);

        return "cadastrado com sucesso" + produtos.getCodigo_Produto();
    } catch (Exception e) {
        e.printStackTrace();
       e.printStackTrace();
            return "produto nao cadastrado";
    }
    }

}
