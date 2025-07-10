package com.pdv.papelaria.repository;

import com.pdv.papelaria.entities.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

    Produto findProdutoByCodigoProduto(Long codigoProduto);

    Produto findProdutoByProduto(String produto);

    List<Produto> findByProdutoStartingWithIgnoreCase(String produto);

    void deleteByCodigoProduto(Long codigoProduto);

    void deleteByProduto(String produto);

}
