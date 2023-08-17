package com.mensal.sliceCtrl.service;

import com.mensal.sliceCtrl.DTO.*;
import com.mensal.sliceCtrl.entity.*;
import com.mensal.sliceCtrl.entity.enums.FormasDePagamento;
import com.mensal.sliceCtrl.entity.enums.Status;
import com.mensal.sliceCtrl.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;
    private final PizzaRepository pizzaRepository;
    private final ClienteRepository clienteRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final PedidoProdutoRepository pedidoProdutoRepository;
    private final PedidoPizzaRepository pedidoPizzaRepository;
    private final PagamentoRepository pagamentoRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PedidoService(PedidoRepository pedidoRepository,
                         ProdutoRepository produtoRepository,
                         PizzaRepository pizzaRepository,
                         ClienteRepository clienteRepository,
                         FuncionarioRepository funcionarioRepository,
                         PedidoProdutoRepository pedidoProdutoRepository,
                         PedidoPizzaRepository pedidoPizzaRepository,
                         PagamentoRepository pagamentoRepository,
                         ModelMapper modelMapper) {
        this.pedidoRepository = pedidoRepository;
        this.produtoRepository = produtoRepository;
        this.pizzaRepository = pizzaRepository;
        this.clienteRepository = clienteRepository;
        this.funcionarioRepository = funcionarioRepository;
        this.pedidoProdutoRepository = pedidoProdutoRepository;
        this.pedidoPizzaRepository = pedidoPizzaRepository;
        this.pagamentoRepository = pagamentoRepository;
        this.modelMapper = modelMapper;
    }


    public PedidosDTO findById(Long id) {
        try {
            Pedidos pedidos = pedidoRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado com o ID: " + id));
            return toPedidosDTO(pedidos);
        } catch (EntityNotFoundException ex) {
            throw new RuntimeException("Ocorreu um erro ao tentar recuperar o pedido.", ex);
        }
    }

    public List<PedidosDTO> findAll() {
        return pedidoRepository.findAll().stream().map(this::toPedidosDTO).toList();
    }

    public List<PedidosDTO> findByStatus(Status status) {
        return pedidoRepository.findByStatus(status).stream().map(this::toPedidosDTO).toList();
    }

    public List<PedidosDTO> findByCliente_Id(Long clienteId) {
        return pedidoRepository.findByCliente(clienteId).stream().map(this::toPedidosDTO).toList();
    }

    public List<PedidosDTO> findByFuncionario_Id(Long funcionarioId) {
        return pedidoRepository.findByFunc(funcionarioId).stream().map(this::toPedidosDTO).toList();
    }

    public List<PedidosDTO> findByForEntrega() {
        return pedidoRepository.findByForEntrega().stream().map(this::toPedidosDTO).toList();
    }

    public List<PedidosDTO> findByForTakeaway() {
        return pedidoRepository.findByForTakeaway().stream().map(this::toPedidosDTO).toList();
    }

    public List<PedidosDTO> findByForDineIn() {
        return pedidoRepository.findByForDineIn().stream().map(this::toPedidosDTO).toList();
    }

    public List<PedidosDTO> findOrdersWithPendingPayments() {
        return pedidoRepository.findOrdersWithPendingPayments().stream().map(this::toPedidosDTO).toList();
    }

    public PedidosDTO toPedidosDTO(Pedidos pedidos) {
        return modelMapper.map(pedidos, PedidosDTO.class);
    }

    public Pedidos efetuarPedido(Long clienteId, FormasDePagamento formDePagamento) {
        return null;
    }

    public Pedidos updateOrder(Long clienteId, Long pedidoId, Status status) {
        return  null;
    }
}
