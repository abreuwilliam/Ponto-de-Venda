package Ponto.de.Venda.PDV.alterarProduto;


import java.security.Provider.Service;
import java.util.List;
import java.util.Map;

import org.apache.naming.ServiceRef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")  // Permite requisições de qualquer origem
@RequestMapping("/alterar")
public class TelaAlterarController {

    @Autowired
    private Alterar alterar;

    @PutMapping("/{codigoProduto}")  // Inclua o código do produto na URL
    public int postMethod(@PathVariable String codigoProduto, @RequestBody produtosAlterar document) {
        return alterar.Alterar(document);
    }
}
