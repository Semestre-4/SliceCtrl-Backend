package com.mensal.sliceCtrl.service;

import com.mensal.sliceCtrl.DTO.ClientesDTO;
import com.mensal.sliceCtrl.DTO.PedidosDTO;
import com.mensal.sliceCtrl.entity.*;
import com.mensal.sliceCtrl.repository.ClienteRepository;
import com.mensal.sliceCtrl.repository.EnderecoRepository;
import com.mensal.sliceCtrl.repository.PedidoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PedidoService(PedidoRepository pedidoRepository,
                          ModelMapper modelMapper) {
        this.pedidoRepository = pedidoRepository;
        this.modelMapper = modelMapper;
    }

    public Pedidos toPedidos(PedidosDTO pedidosDTO, List<Produtos> produtosList, List<Pizzas> pizzasList) {
        Pedidos pedido = modelMapper.map(pedidosDTO, Pedidos.class);
        pedido.setProdutos(produtosList);
        pedido.setPizzas(pizzasList);
        return pedido;
    }

    public PedidosDTO ToPedidosDTO(Pedidos pedidos) {
        return modelMapper.map(pedidos, PedidosDTO.class);
    }

}
