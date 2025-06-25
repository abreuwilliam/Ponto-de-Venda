package com.pdv.papelaria.controller;

import com.pdv.papelaria.dto.ProdutoDto;
import com.pdv.papelaria.exception.RecursoNaoEncontradoException;
import com.pdv.papelaria.exception.RequisicaoInvalidaException;
import com.pdv.papelaria.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    ProdutoService produtoService;

    @Operation(summary = "Buscar produto por código")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto encontrado"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @GetMapping("/{codigoProduto}")
    public ResponseEntity<ProdutoDto> buscarProdutoPorCodigo(@PathVariable Long codigoProduto) {
        ProdutoDto produto = produtoService.buscarPorCodigo(codigoProduto);

        if (produto != null) {
            return ResponseEntity.ok(produto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/caixa")
    public ResponseEntity<Map<String, Object>> buscarProdutoPorCodigoPost(@RequestBody Map<String, String> payload) {
        try {
            String codigo = payload.get("codigo_Produto");
            ProdutoDto produto = produtoService.buscarPorCodigo(Long.parseLong(codigo));

            if (produto != null) {
                Map<String, Object> resposta = new HashMap<>();
                resposta.put("descricao", produto.getProduto()); // ou getNome()
                resposta.put("preco", produto.getPreco());
                return ResponseEntity.ok(resposta);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @Operation(summary = "Atualizar um produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso")
    })
    @PutMapping("/{codigoProduto}")
    public ResponseEntity<String> atualizarProduto(@PathVariable Long codigoProduto, @RequestBody ProdutoDto produtoDto) {
        produtoDto.setCodigoProduto(codigoProduto); // garante que o código do path será usado
        produtoService.atualizar(produtoDto); // novo método no service para atualizar corretamente
        return ResponseEntity.ok("Produto atualizado com sucesso.");
    }

    @Operation(summary = "Baixar estoque de vários produtos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estoque atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Campos obrigatórios faltando"),
            @ApiResponse(responseCode = "500", description = "Erro interno ao atualizar estoque")
    })
    @PostMapping("/baixa")
    public ResponseEntity<String> baixarEstoque(@RequestBody List<Map<String, Object>> produtos) {
        try {
            for (Map<String, Object> produto : produtos) {
                if (!produto.containsKey("descricao") || !produto.containsKey("quantidade")) {
                    return ResponseEntity.badRequest().body("Campos obrigatórios faltando.");
                }

                String descricao = String.valueOf(produto.get("descricao"));
                int quantidade = Integer.parseInt(produto.get("quantidade").toString());

                produtoService.baixarEstoque(descricao, quantidade);
            }
            return ResponseEntity.ok("Estoque atualizado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar o estoque: " + e.getMessage());
        }
    }

    @Operation(summary = "Cadastrar um novo produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Campos obrigatórios não preenchidos ou produto já existente")
    })
    @PostMapping("/cadastro")
    public ResponseEntity<String> cadastrar(@RequestBody ProdutoDto produtoDto) {
        if ((produtoDto.getProduto() == null) || (produtoDto.getPreco() <= 0)) {
            throw new RequisicaoInvalidaException("Campos obrigatórios não preenchidos: descrição e preço.");
        }

        ProdutoDto produtoExistente = produtoService.buscarPorDescricao(produtoDto.getProduto());
        if (produtoExistente != null) {
            throw new RequisicaoInvalidaException("Produto já existente com a descrição: " + produtoDto.getProduto());
        }
        produtoService.salvar(produtoDto);
        return ResponseEntity.ok("Produto cadastrado com sucesso.");
    }

    @Operation(summary = "Processar um produto no caixa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Processado com sucesso")
    })
    @PostMapping("/{codigoProduto}")
    public ResponseEntity<String> processarCaixa(@PathVariable Long codigoProduto) {
        String resultado = produtoService.processarCaixa(codigoProduto);
        return ResponseEntity.ok(resultado);
    }

    @Operation(summary = "Consultar produtos pela inicial da descrição")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum produto encontrado")
    })
    @PostMapping("/consulta")
    public ResponseEntity<List<ProdutoDto>> consultarProdutos(@RequestBody String inicialDoProduto) {
        List<ProdutoDto> produtos = produtoService.buscarPorInicioDaDescricao(inicialDoProduto);

        if (produtos == null || produtos.isEmpty()) {
            throw new RecursoNaoEncontradoException("Nenhum produto encontrado com a inicial: " + inicialDoProduto);
        }
        return ResponseEntity.ok(produtos);
    }

    @Operation(summary = "Deletar produto por código")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto deletado com sucesso")
    })
    @DeleteMapping("/{codigoProduto}")
    public ResponseEntity<String> deletarPorCodigo(@PathVariable Long codigoProduto) {
        produtoService.deletarProduto(codigoProduto);
        return ResponseEntity.ok("Produto deletado com sucesso.");
    }

    @Operation(summary = "Deletar produto por nome")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto deletado com sucesso")
    })
    @PostMapping("/delete")
    public ResponseEntity<String> deletarPorNome(@RequestBody String nomeProduto) {
        produtoService.deletarProdutoPorNome(nomeProduto);
        return ResponseEntity.ok("Produto com descrição '" + nomeProduto + "' deletado com sucesso.");
    }

}
