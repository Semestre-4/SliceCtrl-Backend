package com.mensal.sliceCtrl.service;

import com.mensal.sliceCtrl.DTO.*;
import com.mensal.sliceCtrl.entity.*;
import com.mensal.sliceCtrl.entity.enums.FormasDePagamento;
import com.mensal.sliceCtrl.entity.enums.Status;
import com.mensal.sliceCtrl.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
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

    
    public PedidosDTO iniciarPedido(Long clienteId, Pedidos pedido,Long funcId) {
        Clientes clientes = clienteRepository.findById(clienteId).orElse(null);
        Funcionarios funcionarios = funcionarioRepository.findById(funcId).orElse(null);
        if (clientes == null && funcionarios == null){
            throw new IllegalArgumentException("Registro do Cliente ou Funcionario não encontrados");
        }
        pedido.setCliente(clientes);
        pedido.setFuncionario(funcionarios);
        pedido.setStatus(Status.PENDENTE);
        pedidoRepository.save(pedido);
        return toPedidosDTO(pedido);
    }

    @Transactional
    public Pedidos addProdutoToPedido(Long pedidoId, PedidoProdutoDTO pedidoProdutoDTO) {
        Pedidos pedido = pedidoRepository.findById(pedidoId).orElse(null);
        if (pedido == null) {
            throw new IllegalArgumentException("Pedido não encontrado com o ID: " + pedidoId);
        }

        PedidoProduto pedidoProduto = toPedidoProduto(pedidoProdutoDTO);
        pedidoProduto.setPedido(pedido);
        pedidoProdutoRepository.save(pedidoProduto);

        pedido.getProdutos().add(pedidoProduto);
        return pedidoRepository.save(pedido);
    }

    public Pedidos addPizzaToPedido(Long pedidoId, PedidoPizzaDTO pedidoPizzaDTO) {
        Pedidos pedido = pedidoRepository.findById(pedidoId).orElse(null);
        if (pedido == null) {
            throw new IllegalArgumentException("Pedido não encontrado com o ID: " + pedidoId);
        }
        PedidoPizza pedidoPizza = toPedidoPizza(pedidoPizzaDTO);
        pedido.getPizzas().add(pedidoPizza);
        return pedidoRepository.save(pedido);
    }

    @Transactional
    public Pedidos efetuarPedido(Long pedidoId, FormasDePagamento formDePagamento) {
        try {
            Pedidos pedido = pedidoRepository.findById(pedidoId).orElse(null);
            if (pedido == null){
                throw new IllegalArgumentException("Pedido não encontrado com o ID: " + pedidoId);
            }
            Pagamento pagamento = new Pagamento();
            pagamento.setFormasDePagamento(formDePagamento);
            pagamento.setPedido(pedido);
            pagamentoRepository.save(pagamento);
            pedido.setPagamento(pagamento);

            // Calculate the total amount
            Double totalPedidoAmount = calculateTotalPedidoAmount(pedido);
            pedido.setValorTotal(totalPedidoAmount);

            pedido.setStatus(Status.PAGO);
             return pedidoRepository.save(pedido);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to finalize pedido.");
        }
    }

    private double calculateTotalPedidoAmount(Pedidos pedido) {
        double productsTotal = calculateProductsTotal(pedido);
        double pizzasTotal = calculatePizzasTotal(pedido);
        double deliveryTotal = pedido.isForEntrega() ? 10.0 : 0.0;
        return productsTotal + pizzasTotal + deliveryTotal;
    }

    private double calculateProductsTotal(Pedidos pedido) {
        return pedido.getProdutos().stream()
                .mapToDouble(this::calculatePedidoProdutoTotal)
                .sum();
    }

    private double calculatePizzasTotal(Pedidos pedido) {
        return pedido.getPizzas().stream()
                .mapToDouble(this::calculatePedidoPizzaTotal)
                .sum();
    }

    private double calculatePedidoProdutoTotal(PedidoProduto pedidoProduto) {
        return pedidoProduto.getQtdePedida() * pedidoProduto.getProduto().getPreco();
    }

    private double calculatePedidoPizzaTotal(PedidoPizza pedidoPizza) {
        return pedidoPizza.getQtdePedida() * pedidoPizza.getPizza().getPreco();
    }



    private PedidoProduto toPedidoProduto(PedidoProdutoDTO pedidoProdutoDTO) {
        PedidoProduto pedidoProduto = modelMapper.map(pedidoProdutoDTO, PedidoProduto.class);

        Produtos produtos = produtoRepository.findById(pedidoProdutoDTO.getProduto().getId()).orElse(null);
        if (produtos == null) {
            throw new IllegalArgumentException("Produto não encontrado com o ID: " + pedidoProdutoDTO.getProduto().getId());
        }
        Pedidos pedidos = pedidoRepository.findById(pedidoProdutoDTO.getPedido().getId()).orElse(null);
        if (pedidos == null) {
            throw new IllegalArgumentException("Pedido não encontrado com o ID: " + pedidoProdutoDTO.getProduto().getId());
        }

        pedidoProduto.setPedido(pedidos);
        pedidoProduto.setProduto(produtos);

        return pedidoProduto;
    }


    private PedidoPizza toPedidoPizza(PedidoPizzaDTO pedidoPizzaDTO) {
        PedidoPizza pedidoPizza = modelMapper.map(pedidoPizzaDTO,PedidoPizza.class);
        Pizzas pizzas = modelMapper.map(pedidoPizzaDTO.getPizza(),Pizzas.class);
        pedidoPizza.setPizza(pizzas);
        return pedidoPizza;
    }

    public Pedidos updateOrder(Long pedidoId) {
        return null;
    }
}
