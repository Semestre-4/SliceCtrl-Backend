package com.mensal.sliceCtrl.service;

import com.mensal.sliceCtrl.DTO.PedidosDTO;
import com.mensal.sliceCtrl.entity.*;
import com.mensal.sliceCtrl.entity.enums.Status;
import com.mensal.sliceCtrl.repository.PedidoRepository;
import jakarta.persistence.EntityNotFoundException;
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

    public Pedidos efetuarPedido(PedidosDTO pedidosDTO) {
        return null;
    }

    public Pedidos updatePedido(Long id, PedidosDTO pedidosDTO) {
        return null;
    }

    public void deletePedido(Long id) {
        Pedidos pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido com ID = : " + id + "nao encontrado"));
        pedidoRepository.delete(pedido);
    }

    public Pedidos toPedidos(PedidosDTO pedidosDTO, List<Produtos> produtosList, List<Pizzas> pizzasList) {
        Pedidos pedido = modelMapper.map(pedidosDTO, Pedidos.class);
        pedido.setProdutos(produtosList);
        pedido.setPizzas(pizzasList);
        return pedido;
    }

    public PedidosDTO toPedidosDTO(Pedidos pedidos) {
        return modelMapper.map(pedidos, PedidosDTO.class);
    }

}
