package com.pdv.papelaria.controller;

import com.pdv.papelaria.dto.ProdutoDto;
import com.pdv.papelaria.exception.RequisicaoInvalidaException;
import com.pdv.papelaria.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/produto")
@Slf4j
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
        log.info("Buscando produto por código: {}", codigoProduto);
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
            log.info("Consulta de produto via caixa. Código recebido: {}", codigo);

            ProdutoDto produto = produtoService.buscarPorCodigo(Long.parseLong(codigo));

            if (produto != null) {
                Map<String, Object> resposta = new HashMap<>();
                resposta.put("descricao", produto.getProduto());
                resposta.put("preco", produto.getPreco());
                return ResponseEntity.ok(resposta);
            } else {
                log.warn("Produto não encontrado para o código: {}", codigo);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            log.error("Erro ao buscar produto no caixa: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Atualizar um produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso")
    })
    @PutMapping("/{codigoProduto}")
    public ResponseEntity<String> atualizarProduto(@PathVariable Long codigoProduto, @RequestBody ProdutoDto produtoDto) {
        produtoDto.setCodigoProduto(codigoProduto);
        log.info("Atualizando produto com código: {}", codigoProduto);
        produtoService.atualizar(produtoDto);
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
        log.info("Iniciando baixa de estoque para {} produtos", produtos.size());

        try {
            for (Map<String, Object> produto : produtos) {
                if (!produto.containsKey("descricao") || !produto.containsKey("quantidade")) {
                    log.warn("Requisição incompleta: faltando campos obrigatórios.");
                    return ResponseEntity.badRequest().body("Campos obrigatórios faltando.");
                }

                String descricao = String.valueOf(produto.get("descricao"));
                int quantidade = Integer.parseInt(produto.get("quantidade").toString());

                log.info("Baixando {} unidades do produto: {}", quantidade, descricao);
                produtoService.baixarEstoque(descricao, quantidade);
            }
            return ResponseEntity.ok("Estoque atualizado com sucesso.");
        } catch (Exception e) {
            log.error("Erro ao atualizar o estoque: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar o estoque: " + e.getMessage());
        }
    }

    @Operation(summary = "Cadastrar um novo produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Campos obrigatórios não preenchidos ou produto já existente")
    })
    @PostMapping("/cadastro")
    public ResponseEntity<String> cadastrar(@RequestBody ProdutoDto produtoDto) {
        log.info("Requisição de cadastro recebida: {}", produtoDto);

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
        log.info("Processando venda para produto com código: {}", codigoProduto);
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
        log.info("Consulta por produtos com descrição iniciando em: {}", inicialDoProduto);
        List<ProdutoDto> produtos = produtoService.buscarPorInicioDaDescricao(inicialDoProduto);

        if (produtos == null || produtos.isEmpty()) {
            log.info("Nenhum produto encontrado com a inicial: {}", inicialDoProduto);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(produtos);
    }

    @Operation(summary = "Deletar produto por código")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto deletado com sucesso")
    })
    @DeleteMapping("/{codigoProduto}")
    public ResponseEntity<String> deletarPorCodigo(@PathVariable Long codigoProduto) {
        log.warn("Solicitada exclusão do produto com código: {}", codigoProduto);
        produtoService.deletarProduto(codigoProduto);
        return ResponseEntity.ok("Produto deletado com sucesso.");
    }

    @Operation(summary = "Deletar produto por nome")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto deletado com sucesso")
    })
    @PostMapping("/delete")
    public ResponseEntity<String> deletarPorNome(@RequestBody String nomeProduto) {
        log.warn("Solicitada exclusão do produto com nome: {}", nomeProduto);
        produtoService.deletarProdutoPorNome(nomeProduto);
        return ResponseEntity.ok("Produto com descrição '" + nomeProduto + "' deletado com sucesso.");
    }
}
