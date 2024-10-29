package Ponto.de.Venda.PDV.ConsultaProduto;

import java.util.List;

import java.util.ArrayList;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class consulta {

    @Autowired
    private ServiceConsulta serviceConsulta;
    
    public List<ModelEstoqueConsulta> realizarConsulta(String consulta){
        try {
            List<ModelEstoqueConsulta> consultas = serviceConsulta.pesquisarProdutoLike(consulta);
            return consultas;
            
        } catch (Exception e) {
            e.printStackTrace();
            // Cria um objeto ModelEstoqueConsulta com a mensagem de erro
            ModelEstoqueConsulta erro = new ModelEstoqueConsulta();
            erro.setNome("Produto não encontrado"); // Supondo que exista um método setNome
            // Adiciona à lista de consultas
            List<ModelEstoqueConsulta> listaErro = new ArrayList<>();
            listaErro.add(erro);
            return listaErro;

    }
    
}}
