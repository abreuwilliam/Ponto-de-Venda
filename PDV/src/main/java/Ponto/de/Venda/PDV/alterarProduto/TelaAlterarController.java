package Ponto.de.Venda.PDV.alterarProduto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")  
@RequestMapping("/alterar")
public class TelaAlterarController {

    @Autowired
    private Alterar alterar;

    @PutMapping("/{codigoProduto}")  
    public Long postMethod(@PathVariable String codigoProduto, @RequestBody produtosAlterar document) {
        return alterar.Alterar(document);
    }
}
