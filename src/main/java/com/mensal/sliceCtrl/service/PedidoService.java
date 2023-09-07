package com.mensal.sliceCtrl.service;

import com.mensal.sliceCtrl.DTO.*;
import com.mensal.sliceCtrl.entity.*;
import com.mensal.sliceCtrl.entity.enums.FormaDeEntrega;
import com.mensal.sliceCtrl.entity.enums.FormasDePagamento;
import com.mensal.sliceCtrl.entity.enums.Status;
import com.mensal.sliceCtrl.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

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
    private final SaboresRepository saboresRepository;
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
                         SaboresRepository saboresRepository,
                         ModelMapper modelMapper) {
        this.pedidoRepository = pedidoRepository;
        this.produtoRepository = produtoRepository;
        this.pizzaRepository = pizzaRepository;
        this.clienteRepository = clienteRepository;
        this.funcionarioRepository = funcionarioRepository;
        this.pedidoProdutoRepository = pedidoProdutoRepository;
        this.pedidoPizzaRepository = pedidoPizzaRepository;
        this.pagamentoRepository = pagamentoRepository;
        this.saboresRepository = saboresRepository;
        this.modelMapper = modelMapper;
    }

    // Método para encontrar um pedido pelo ID
    public PedidosDTO findById(Long id) {
        try {
            Pedidos pedidos = pedidoRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado com o ID: " + id));
            return toPedidosDTO(pedidos);
        } catch (EntityNotFoundException ex) {
            throw new RuntimeException("Ocorreu um erro ao tentar recuperar o pedido.", ex);
        }
    }

    // Método para encontrar todos os pedidos
    public List<PedidosDTO> findAll() {
        return pedidoRepository.findAll().stream().map(this::toPedidosDTO).toList();
    }

    // Métodos para encontrar pedidos por status, cliente, funcionário e forma de entrega
    public List<PedidosDTO> findByStatus(Status status) {
        return pedidoRepository.findByStatus(status).stream().map(this::toPedidosDTO).toList();
    }

    public List<PedidosDTO> findByformaDeEntrega(FormaDeEntrega formaDeEntrega) {
        return pedidoRepository.findByformaDeEntrega(formaDeEntrega).stream().map(this::toPedidosDTO).toList();
    }

    public List<PedidosDTO> findByCliente_Id(Long clienteId) {
        return pedidoRepository.findByCliente(clienteId).stream().map(this::toPedidosDTO).toList();
    }

    public List<PedidosDTO> findByFuncionario_Id(Long funcionarioId) {
        return pedidoRepository.findByFunc(funcionarioId).stream().map(this::toPedidosDTO).toList();
    }


    // Método para encontrar pedidos com pagamentos pendentes
    public List<PedidosDTO> findOrdersWithPendingPayments() {
        return pedidoRepository.findOrdersWithPendingPayments().stream().map(this::toPedidosDTO).toList();
    }

    // Método para iniciar um novo pedido
    @Transactional
    public PedidosDTO iniciarPedido(Long clienteId, Pedidos pedido, Long funcId, FormaDeEntrega formaDeEntrega) {
        // Encontrar o cliente pelo ID
        Clientes clientes = clienteRepository.findById(clienteId).orElse(null);

        // Encontrar o funcionário pelo ID
        Funcionarios funcionarios = funcionarioRepository.findById(funcId).orElse(null);

        // Verificar se o cliente e o funcionário foram encontrados
        if (clientes == null && funcionarios == null) {
            throw new IllegalArgumentException("Registro do Cliente ou Funcionario não encontrados");
        }

        // Definir informações do pedido
        pedido.setCliente(clientes);
        pedido.setFuncionario(funcionarios);
        pedido.setStatus(Status.PENDENTE);
        pedido.setFormaDeEntrega(formaDeEntrega);
        pedidoRepository.save(pedido);

        return toPedidosDTO(pedido);
    }

    // Método para adicionar um produto ao pedido
    @Transactional
    public Pedidos addProdutoToPedido(Long pedidoId, PedidoProdutoDTO pedidoProdutoDTO) {
        // Encontrar o pedido pelo ID
        Pedidos pedido = pedidoRepository.findById(pedidoId).orElse(null);

        // Verificar se o pedido foi encontrado
        if (pedido == null) {
            throw new IllegalArgumentException("Pedido não encontrado com o ID: " + pedidoId);
        }

        // Verificar o status do pedido
        if (pedido.getStatus() == Status.PAGO) {
            throw new IllegalArgumentException("Pedido fechado, crie um novo pedido");
        }

        // Converter DTO para entidade PedidoProduto
        PedidoProduto pedidoProduto = toPedidoProduto(pedidoProdutoDTO);

        // Verificar disponibilidade do produto em estoque
        if (pedidoProduto.getProduto().getQtdeEstoque() <= 0) {
            throw new IllegalArgumentException("O item selecionado encontra-se " +
                    "atualmente indisponível em nosso estoque.");
        }

        // Associar o pedido ao produto
        pedidoProduto.setPedido(pedido);
        pedidoProdutoRepository.save(pedidoProduto);

        pedido.getProdutos().add(pedidoProduto);
        return pedidoRepository.save(pedido);
    }

    // Método para adicionar uma pizza ao pedido
    @Transactional
    public Pedidos addPizzaToPedido(Long pedidoId, PedidoPizzaDTO pedidoPizzaDTO) {
        // Encontrar o pedido pelo ID
        Pedidos pedido = pedidoRepository.findById(pedidoId).orElse(null);

        // Verificar se o pedido foi encontrado
        if (pedido == null) {
            throw new IllegalArgumentException("Pedido não encontrado com o ID: " + pedidoId);
        }

        // Verificar o status do pedido
        if (pedido.getStatus() == Status.PAGO) {
            throw new IllegalArgumentException("Pedido fechado, crie um novo pedido");
        }

        // Converter DTO para entidade PedidoPizza
        PedidoPizza pedidoPizza = toPedidoPizza(pedidoPizzaDTO);

        //TODO : VERIFICAR QTDE. DE SABORES

        // Associar o pedido à pizza
        pedidoPizza.setPedido(pedido);
        pedidoPizzaRepository.save(pedidoPizza);

        pedido.getPizzas().add(pedidoPizza);
        return pedidoRepository.save(pedido);
    }

    // Método para efetuar um pedido com um método de pagamento
    @Transactional
    public Pedidos efetuarPedido(Long pedidoId, FormasDePagamento formDePagamento) {
        try {
            // Encontrar o pedido pelo ID
            Pedidos pedido = pedidoRepository.findById(pedidoId).orElse(null);

            // Verificar se o pedido foi encontrado
            if (pedido == null) {
                throw new IllegalArgumentException("Pedido não encontrado com o ID: " + pedidoId);
            }

            // Criar um novo pagamento
            Pagamento pagamento = new Pagamento();
            pagamento.setFormasDePagamento(formDePagamento);
            pagamento.setPedido(pedido);
            pagamentoRepository.save(pagamento);
            pedido.setPagamento(pagamento);

            // Calcular o valor total do pedido
            Double totalPedidoAmount = calculateTotalPedidoAmount(pedido);
            pedido.setValorTotal(totalPedidoAmount);

            // Definir o status do pedido como PAGO
            pedido.setStatus(Status.PAGO);
            return pedidoRepository.save(pedido);
        } catch (Exception e) {
            throw new IllegalArgumentException("Erro ao finalizar o pedido.");
        }
    }

    // Método para atualizar um pedido
    @Transactional
    public Pedidos updateOrder(Long pedidoId) {
        // Encontrar o pedido pelo ID
        Pedidos existingPedido = pedidoRepository.findById(pedidoId).orElse(null);

        // Verificar se o pedido foi encontrado
        if (existingPedido == null) {
            throw new IllegalArgumentException("Pedido não encontrado com o ID: " + pedidoId);
        }

        // Verificar o status do pedido
        if (existingPedido.getStatus() != Status.PENDENTE) {
            throw new IllegalArgumentException(("O pedido não pode ser alterado"));
        } else {
            // Salvar as alterações no pedido
            return pedidoRepository.save(existingPedido);
        }
    }

    // Método para calcular o valor total do pedido
    private double calculateTotalPedidoAmount(Pedidos pedido) {
        //TODO : DELIVEY FEE
        double productsTotal = calculateProductsTotal(pedido);
        double pizzasTotal = calculatePizzasTotal(pedido);
        double deliveryTotal = 0.0;
        return productsTotal + pizzasTotal + deliveryTotal;
    }

    // Método para calcular o total dos produtos no pedido
    private double calculateProductsTotal(Pedidos pedido) {
        return pedido.getProdutos().stream()
                .mapToDouble(this::calculatePedidoProdutoTotal)
                .sum();
    }

    // Método para calcular o total das pizzas no pedido
    private double calculatePizzasTotal(Pedidos pedido) {
        return pedido.getPizzas().stream()
                .mapToDouble(this::calculatePedidoPizzaTotal)
                .sum();
    }

    // Método para calcular o total de um item do tipo PedidoProduto
    private double calculatePedidoProdutoTotal(PedidoProduto pedidoProduto) {
        return pedidoProduto.getQtdePedida() * pedidoProduto.getProduto().getPreco();
    }

    // Método para calcular o total de um item do tipo PedidoPizza
    private double calculatePedidoPizzaTotal(PedidoPizza pedidoPizza) {
        return pedidoPizza.getQtdePedida() * pedidoPizza.getPizza().getPreco();
    }

    // Método para converter um DTO em um objeto PedidoProduto
    private PedidoProduto toPedidoProduto(PedidoProdutoDTO pedidoProdutoDTO) {
        PedidoProduto pedidoProduto = modelMapper.map(pedidoProdutoDTO, PedidoProduto.class);

        // Encontrar o produto pelo ID
        Produtos produtos = produtoRepository.findById(pedidoProdutoDTO.getProduto().getId()).orElse(null);
        if (produtos == null) {
            throw new IllegalArgumentException("Produto não encontrado com o ID: "
                    + pedidoProdutoDTO.getProduto().getId());
        }

        // Encontrar o pedido pelo ID
        Pedidos pedidos = pedidoRepository.findById(pedidoProdutoDTO.getPedido().getId()).orElse(null);
        if (pedidos == null) {
            throw new IllegalArgumentException("Pedido não encontrado com o ID: "
                    + pedidoProdutoDTO.getProduto().getId());
        }

        // Definir o pedido e o produto no objeto PedidoProduto
        pedidoProduto.setPedido(pedidos);
        pedidoProduto.setProduto(produtos);

        return pedidoProduto;
    }

    // Método para converter um objeto Pedidos em um DTO
    public PedidosDTO toPedidosDTO(Pedidos pedidos) {
        return modelMapper.map(pedidos, PedidosDTO.class);
    }

    // Método para converter um DTO em um objeto PedidoPizza
    private PedidoPizza toPedidoPizza(PedidoPizzaDTO pedidoPizzaDTO) {
        PedidoPizza pedidoPizza = modelMapper.map(pedidoPizzaDTO, PedidoPizza.class);

        // Encontrar a pizza pelo ID
        Pizzas pizzas = pizzaRepository.findById(pedidoPizzaDTO.getPizza().getId()).orElse(null);
        if (pizzas == null) {
            throw new IllegalArgumentException("Pizza não encontrada com o ID: " + pedidoPizzaDTO.getPizza().getId());
        }

        // Encontrar o pedido pelo ID
        Pedidos pedidos = pedidoRepository.findById(pedidoPizza.getPedido().getId()).orElse(null);
        if (pedidos == null) {
            throw new IllegalArgumentException("Pedido não encontrado");
        }

        Sabores sabores = saboresRepository.findById(pedidoPizza.getSabor().getId()).orElse(null);


        // Definir o pedido e a pizza no objeto PedidoPizza
        pedidoPizza.setPedido(pedidos);
        pedidoPizza.setPizza(pizzas);
        pedidoPizza.setSabor(sabores);

        return pedidoPizza;
    }

}
