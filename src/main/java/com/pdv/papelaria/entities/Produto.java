package com.pdv.papelaria.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "estoque")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "codigo_Produto")
    private Long codigoProduto;

    @Column(name = "produto")
    private String produto;

    @Column(name = "preco")
    private double preco;

    @Column(name = "quantidade_Estoque")
    private int quantidadeEstoque;

}


