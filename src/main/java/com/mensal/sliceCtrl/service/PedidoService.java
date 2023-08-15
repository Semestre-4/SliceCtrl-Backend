package com.mensal.sliceCtrl.service;

import com.mensal.sliceCtrl.DTO.PedidosDTO;
import com.mensal.sliceCtrl.DTO.PizzasDTO;
import com.mensal.sliceCtrl.DTO.ProdutosDTO;
import com.mensal.sliceCtrl.entity.*;
import com.mensal.sliceCtrl.entity.enums.Status;
import com.mensal.sliceCtrl.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Transient;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;
    private final PizzaRepository pizzaRepository;
    private final ClienteRepository clienteRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PedidoService(PedidoRepository pedidoRepository,
                         ProdutoRepository produtoRepository,
                         PizzaRepository pizzaRepository,
                         ClienteRepository clienteRepository,
                         FuncionarioRepository funcionarioRepository,
                         ModelMapper modelMapper) {
        this.pedidoRepository = pedidoRepository;
        this.produtoRepository = produtoRepository;
        this.pizzaRepository = pizzaRepository;
        this.clienteRepository = clienteRepository;
        this.funcionarioRepository = funcionarioRepository;
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

    @Transactional
    public Pedidos efetuarPedido(PedidosDTO pedidosDTO) {
        if (pedidosDTO == null) {
            throw new IllegalArgumentException("O objeto PedidosDTO não pode ser nulo");
        }

        List<Produtos> produtosList = new ArrayList<>();
        List<Pizzas> pizzasList = new ArrayList<>();

        Clientes clienteExistente = clienteRepository.findById(pedidosDTO.getClienteId().getId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente com ID = " + pedidosDTO.getClienteId().getId() + " não encontrado"));

        Funcionarios funcFound = funcionarioRepository.findById(pedidosDTO.getFuncionarioId().getId())
                .orElseThrow(() -> new EntityNotFoundException("Funcionario com ID = " + pedidosDTO.getFuncionarioId().getId() + " não encontrado"));

        for (ProdutosDTO produtosDTO : pedidosDTO.getProdutos()) {
            Produtos existingProduto = produtoRepository.findById(produtosDTO.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Produto com ID = " + produtosDTO.getId() + " não encontrado"));
            produtosList.add(existingProduto);
        }

        for (PizzasDTO pizzasDTO : pedidosDTO.getPizzas()) {
            Pizzas existingPizza = pizzaRepository.findById(pizzasDTO.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Pizza com ID = " + pizzasDTO.getId() + " não encontrada"));
            pizzasList.add(existingPizza);
        }

        Pedidos novoPedido = toPedidos(pedidosDTO, produtosList, pizzasList, clienteExistente, funcFound);

        // Calculate the total amount
        BigDecimal totalPedidoAmount = calculateTotalPedidoAmount(novoPedido);
        novoPedido.setValorTotal(totalPedidoAmount);

        Status orderStatus = determineOrderStatus(pedidosDTO);
        novoPedido.setStatus(orderStatus);

        return pedidoRepository.save(novoPedido);
    }


    private Status determineOrderStatus(PedidosDTO pedidosDTO) {
        if (pedidosDTO.isPago()){
            return Status.PAGO;
        }
        return Status.PENDENTE;
    }


    private BigDecimal calculateTotalPedidoAmount(Pedidos pedido) {
        BigDecimal productsTotal = calculateProductsTotal(pedido);
        BigDecimal pizzasTotal = calculatePizzasTotal(pedido);
        BigDecimal deliveryTotal = pedido.isForEntrega() ? BigDecimal.TEN : BigDecimal.ZERO;
        return productsTotal.add(pizzasTotal).add(deliveryTotal);
    }

    private BigDecimal calculatePizzasTotal(Pedidos pedido) {
        return pedido.getPizzas().stream()
                .map(pizzas -> BigDecimal.valueOf(pizzas.getPreco()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateProductsTotal(Pedidos pedido) {
        return pedido.getProdutos().stream()
                .map(produtos -> BigDecimal.valueOf(produtos.getPreco()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Pedidos updatePedido(Long id, PedidosDTO pedidosDTO) {
        return null;
    }

    public void deletePedido(Long id) {
        Pedidos pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido com ID = : " + id + "nao encontrado"));
        pedidoRepository.delete(pedido);
    }

    public Pedidos toPedidos(PedidosDTO pedidosDTO, List<Produtos> produtosList,
                             List<Pizzas> pizzasList,Clientes clientes,Funcionarios funcionarios) {
        Pedidos pedido = modelMapper.map(pedidosDTO, Pedidos.class);
        pedido.setProdutos(produtosList);
        pedido.setCliente(clientes);
        pedido.setFuncionario(funcionarios);
        pedido.setPizzas(pizzasList);
        return pedido;
    }

    public PedidosDTO toPedidosDTO(Pedidos pedidos) {
        return modelMapper.map(pedidos, PedidosDTO.class);
    }

}
