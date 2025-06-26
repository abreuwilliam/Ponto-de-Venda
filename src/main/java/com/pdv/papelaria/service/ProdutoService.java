package com.pdv.papelaria.service;

import com.pdv.papelaria.aop.LogExecutionTime;
import com.pdv.papelaria.dto.ProdutoDto;
import com.pdv.papelaria.entities.Produto;
import com.pdv.papelaria.exception.RecursoNaoEncontradoException;
import com.pdv.papelaria.exception.RequisicaoInvalidaException;
import com.pdv.papelaria.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProdutoService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProdutoRepository produtoRepository;

    @LogExecutionTime
    @Cacheable(value = "produtocache")
    public List<ProdutoDto> listarTodos() {
        List<Produto> produtos = produtoRepository.findAll();
        log.info("Lista de produtos retornada com sucesso");
        return produtos.stream()
                .map(ProdutoDto::new)
                .collect(Collectors.toList());
    }

    @LogExecutionTime
    @Cacheable(value = "produtocache", key = "#codigo")
    public ProdutoDto buscarPorCodigo(Long codigo) {
        Produto produto = produtoRepository.findProdutoByCodigoProduto(codigo);
        if (produto == null) {
            throw new RecursoNaoEncontradoException("Produto com código '" + codigo + "' não encontrado.");
        }
        log.info("Produto retornado por código com sucesso");
        return new ProdutoDto(produto);
    }

    @LogExecutionTime
    @Transactional
    @CacheEvict(value = "produtocache", allEntries = true)
    public String processarCaixa(Long codigoProduto) {
        ProdutoDto produtoDto = buscarPorCodigo(codigoProduto);

        if (produtoDto.getQuantidadeEstoque() <= 0) {
            throw new RequisicaoInvalidaException("Produto sem estoque disponível.");
        }

        produtoDto.setQuantidadeEstoque(produtoDto.getQuantidadeEstoque() - 1);
        salvar(produtoDto);
        log.info("Caixa processado com sucesso");
        return produtoDto.getProduto() + " - Preço: " + produtoDto.getPreco();
    }

    @LogExecutionTime
    @Cacheable(value = "produtocache", key = "#descricao")
    public ProdutoDto buscarPorDescricao(String descricao) {
        Produto produto = produtoRepository.findProdutoByProduto(descricao);
        if (produto == null) {
            return null;
        }
        log.info("Produto retornado por descrição com sucesso");
        return new ProdutoDto(produto);
    }

    @LogExecutionTime
    @Transactional
    @CacheEvict(value = "produtocache", allEntries = true)
    public void salvar(ProdutoDto produtoDto) {
        Produto produto = modelMapper.map(produtoDto, Produto.class);
        produtoRepository.save(produto);
        log.info("Produto salvo com sucesso");
    }

    @LogExecutionTime
    @Transactional
    @CacheEvict(value = "produtocache", allEntries = true)
    public void baixarEstoque(String descricao, int quantidade) {
        ProdutoDto produtoDto = buscarPorDescricao(descricao);
        if (quantidade <= 0) {
            throw new RequisicaoInvalidaException("Quantidade deve ser maior que zero.");
        }
        produtoDto.setQuantidadeEstoque(produtoDto.getQuantidadeEstoque() - quantidade);
        salvar(produtoDto);
        log.info("Estoque baixado com sucesso");
    }

    @LogExecutionTime
    @Cacheable(value = "produtocache", key = "#produto")
    public List<ProdutoDto> buscarPorInicioDaDescricao(String produto) {
        List<Produto> produtos = produtoRepository.findByProdutoStartingWith(produto);
        log.info("Produtos retornados por início da descrição com sucesso");
        return produtos.stream()
                .map(ProdutoDto::new)
                .collect(Collectors.toList());
    }

    @LogExecutionTime
    @Transactional
    @CacheEvict(value = "produtocache", allEntries = true)
    public void deletarProduto(Long codigoProduto) {
        produtoRepository.deleteByCodigoProduto(codigoProduto);
        log.info("Produto deletado com sucesso pelo código");
    }

    @LogExecutionTime
    @CacheEvict(value = "produtocache", allEntries = true)
    public void deletarProdutoPorNome(String produto) {
        produtoRepository.deleteByProduto(produto);
        log.info("Produto deletado com sucesso pelo nome");
    }

    @LogExecutionTime
    @Transactional
    @CacheEvict(value = "produtocache", allEntries = true)
    public void atualizar(ProdutoDto produtoDto) {
        Produto existente = produtoRepository.findProdutoByCodigoProduto(produtoDto.getCodigoProduto());
        if (existente == null) {
            throw new RecursoNaoEncontradoException("Produto não encontrado para atualização.");
        }
        existente.setProduto(produtoDto.getProduto());
        existente.setPreco(produtoDto.getPreco());
        existente.setQuantidadeEstoque(produtoDto.getQuantidadeEstoque());
        produtoRepository.save(existente);
        log.info("Produto atualizado com sucesso");
    }
}
