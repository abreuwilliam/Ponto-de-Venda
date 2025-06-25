package com.pdv.papelaria.service;


import com.pdv.papelaria.dto.ProdutoDto;
import com.pdv.papelaria.entities.Produto;
import com.pdv.papelaria.exception.RecursoNaoEncontradoException;
import com.pdv.papelaria.exception.RequisicaoInvalidaException;
import com.pdv.papelaria.repository.ProdutoRepository;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class ProdutoService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<ProdutoDto> listarTodos() {
        List<Produto> produtos = produtoRepository.findAll();
        return produtos.stream()
                .map(ProdutoDto::new)
                .collect(Collectors.toList());
    }

    public ProdutoDto buscarPorCodigo(Long codigo) {
        Produto produto = produtoRepository.findProdutoByCodigoProduto(codigo);
        if (produto == null) {
            throw new RecursoNaoEncontradoException("Produto com código '" + codigo + "' não encontrado.");
        }
        return new ProdutoDto(produto);
    }

    public String processarCaixa(Long codigoProduto) {
        ProdutoDto produtoDto = buscarPorCodigo(codigoProduto);

        if (produtoDto.getQuantidadeEstoque() <= 0) {
            throw new RequisicaoInvalidaException("Produto sem estoque disponível.");
        }

        produtoDto.setQuantidadeEstoque(produtoDto.getQuantidadeEstoque() - 1);
        salvar(produtoDto);

        return produtoDto.getProduto() + " - Preço: " + produtoDto.getPreco();
    }


    public ProdutoDto buscarPorDescricao(String descricao) {
        Produto produto = produtoRepository.findProdutoByProduto(descricao);
        if (produto == null) {
            return null; // Produto não encontrado, retorna null
        }
        return new ProdutoDto(produto);
    }


    public void salvar(ProdutoDto produtoDto) {
        Produto produto = modelMapper.map(produtoDto, Produto.class);
        produtoRepository.save(produto);
    }

    public void baixarEstoque(String descricao, int quantidade) {
        ProdutoDto produtoDto = buscarPorDescricao(descricao);
        if (quantidade <= 0) {
            throw new RequisicaoInvalidaException("Quantidade deve ser maior que zero.");
        }
        produtoDto.setQuantidadeEstoque(produtoDto.getQuantidadeEstoque() - quantidade);
        salvar(produtoDto);
    }

    public List<ProdutoDto> buscarPorInicioDaDescricao(String produto) {
        List<Produto> produtos = produtoRepository.findByProdutoStartingWith(produto);
        return produtos.stream()
                .map(ProdutoDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deletarProduto(Long codigoProduto) {
        produtoRepository.deleteByCodigoProduto(codigoProduto);
    }

    public void deletarProdutoPorNome(String produto) {
        produtoRepository.deleteByProduto(produto);
    }

    @Transactional
    public void atualizar(ProdutoDto produtoDto) {
        Produto existente = produtoRepository.findProdutoByCodigoProduto(produtoDto.getCodigoProduto());
        if (existente == null) {
            throw new RecursoNaoEncontradoException("Produto não encontrado para atualização.");
        }
        existente.setProduto(produtoDto.getProduto());
        existente.setPreco(produtoDto.getPreco());
        existente.setQuantidadeEstoque(produtoDto.getQuantidadeEstoque());

        produtoRepository.save(existente);
    }
}
