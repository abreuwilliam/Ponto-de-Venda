package com.pdv.papelaria.service;

import com.pdv.papelaria.dto.PedidosDto;
import com.pdv.papelaria.entities.Pedidos;
import com.pdv.papelaria.entities.Produto;
import com.pdv.papelaria.repository.PedidosRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidosService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    PedidosRepository pedidosRepository;

    public List<Pedidos> exibirTodosPedidos(){
        return pedidosRepository.findAll();
    }
    public void salvarEstoque(PedidosDto pedidosDto){
        Pedidos pedidos = modelMapper.map(pedidosDto, Pedidos.class);
        pedidosRepository.save(pedidos);
    }
    public Pedidos pesquisarPedidos(String pedidos){
        return pedidosRepository.findByPedidos(pedidos);
    }
    public void deletarTodosPedidos(){
        pedidosRepository.deleteAll();
    }
}
