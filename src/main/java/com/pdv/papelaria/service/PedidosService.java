package com.pdv.papelaria.service;

import com.pdv.papelaria.aop.LogExecutionTime;
import com.pdv.papelaria.dto.PedidosDto;
import com.pdv.papelaria.entities.Pedidos;
import com.pdv.papelaria.repository.PedidosRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidosService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    PedidosRepository pedidosRepository;

    @LogExecutionTime
    @Cacheable(value = "produtocache")
    public List<Pedidos> exibirTodosPedidos() {
        return pedidosRepository.findAll();
    }

    @LogExecutionTime
    @Cacheable(value = "produtocache")
    public void salvarEstoque(PedidosDto pedidosDto) {
        Pedidos pedidos = modelMapper.map(pedidosDto, Pedidos.class);
        pedidosRepository.save(pedidos);
    }

    @LogExecutionTime
    @Cacheable(value = "produtocache")
    public Pedidos pesquisarPedidos(String pedidos) {
        return pedidosRepository.findByPedidos(pedidos);
    }

    @LogExecutionTime
    @Cacheable(value = "produtocache")
    @Transactional
    public void deletarTodosPedidos() {
        pedidosRepository.deleteAll();
    }
}
