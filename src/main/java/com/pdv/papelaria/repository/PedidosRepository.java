package com.pdv.papelaria.repository;

import com.pdv.papelaria.entities.Pedidos;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PedidosRepository extends CrudRepository<Pedidos, Integer> {
    List<Pedidos> findAll();

    Pedidos findByPedidos(String pedidos);

    Pedidos save(Pedidos pedidos);
}
