package com.mensal.slicectrl.service;

import com.mensal.slicectrl.dto.*;
import com.mensal.slicectrl.entity.*;
import com.mensal.slicectrl.entity.enums.FormaDeEntrega;
import com.mensal.slicectrl.entity.enums.FormasDePagamento;
import com.mensal.slicectrl.entity.enums.Status;
import com.mensal.slicectrl.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;
    private final PizzaRepository pizzaRepository;
    private final ClienteRepository clienteRepository;
    private final UsuarioRepository usuarioRepository;
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
                         UsuarioRepository usuarioRepository,
                         PedidoProdutoRepository pedidoProdutoRepository,
                         PedidoPizzaRepository pedidoPizzaRepository,
                         PagamentoRepository pagamentoRepository,
                         SaboresRepository saboresRepository,
                         ModelMapper modelMapper) {
        this.pedidoRepository = pedidoRepository;
        this.produtoRepository = produtoRepository;
        this.pizzaRepository = pizzaRepository;
        this.clienteRepository = clienteRepository;
        this.usuarioRepository = usuarioRepository;
        this.pedidoProdutoRepository = pedidoProdutoRepository;
        this.pedidoPizzaRepository = pedidoPizzaRepository;
        this.pagamentoRepository = pagamentoRepository;
        this.saboresRepository = saboresRepository;
        this.modelMapper = modelMapper;
    }

    // Constante para a mensagem de erro
    private static final String PEDIDO_NAO_ENCONTRADO_MSG = "Pedido não encontrado com o ID: ";

    // Método para encontrar um pedido pelo ID
    public PedidosDTO findById(Long id) {
        Pedidos pedidos = pedidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(PEDIDO_NAO_ENCONTRADO_MSG + id));
        return toPedidosDTO(pedidos);
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

    public List<Object[]> findMostUsedSabores() {
        return pedidoPizzaRepository.findMostUsedSabores();
    }

    public List<Produtos> findMostUsedProducts() {
        return pedidoProdutoRepository.findMostUsedProducts();
    }

    public int countPedidosByFormaDePagamento(FormasDePagamento formaDePagamento) {
        return pedidoRepository.countPedidosByFormaDePagamento(formaDePagamento);
    }

    public List<PedidosDTO> findByClienteId(Long clienteId) {
        return pedidoRepository.findByCliente(clienteId).stream().map(this::toPedidosDTO).toList();
    }

    public List<PedidosDTO> findByFuncionarioId(Long funcionarioId) {
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
        Usuario usuario = usuarioRepository.findById(funcId).orElse(null);


        // Verificar se o cliente e o funcionário foram encontrados
        if (clientes == null && usuario == null) {
            throw new IllegalArgumentException("Registro do Cliente ou Funcionario não encontrados");
        }

        List<Pedidos> pendingOrders = pedidoRepository.findByClienteAndStatus(clientes, Status.PENDENTE);

        if (!pendingOrders.isEmpty()) {
            throw new IllegalArgumentException(String.format("Já existe um pedido iniciado com o cliente %s de CPF = %s",
                    clientes.getNome(), clientes.getCpf()));
        }

        // Definir informações do pedido
        pedido.setCliente(clientes);
        pedido.setFuncionario(usuario);
        pedido.setStatus(Status.PENDENTE);
        pedido.setFormaDeEntrega(formaDeEntrega);
        pedido.setValorTotal(Double.parseDouble("0"));

        return toPedidosDTO(pedidoRepository.save(pedido));
    }

    // Método para adicionar um produto ao pedido
    @Transactional
    public Pedidos addProdutoToPedido(Long pedidoId, PedidoProdutoDTO pedidoProdutoDTO) {
        // Encontrar o pedido pelo ID
        Pedidos pedido = pedidoRepository.findById(pedidoId).orElse(null);

        // Verificar se o pedido foi encontrado
        if (pedido == null) {
            throw new IllegalArgumentException(PEDIDO_NAO_ENCONTRADO_MSG + pedidoId);
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
            throw new IllegalArgumentException(PEDIDO_NAO_ENCONTRADO_MSG + pedidoId);
        }

        // Verificar o status do pedido
        if (pedido.getStatus() == Status.PAGO) {
            throw new IllegalArgumentException("Pedido fechado, crie um novo pedido");
        }

        // Converter DTO para entidade PedidoPizza
        PedidoPizza pedidoPizza = toPedidoPizza(pedidoPizzaDTO);

        int maxFlavors = 0;
        switch (pedidoPizza.getPizza().getTamanho()) {
            case P -> maxFlavors = 1;
            case M -> maxFlavors = 2;
            case G -> maxFlavors = 3;
            case XG -> maxFlavors = 4;
            default -> throw new IllegalArgumentException("Tamanho Inválido!");
        }

        // Verificar se o número máximo de sabores foi excedido para o tamanho da pizza.
        if (pedidoPizza.getSabores().size() > maxFlavors) {
            throw new IllegalArgumentException("Número máximo de sabores excedido para o tamanho da pizza.");
        }

        // Associar o pedido à pizza
        pedidoPizza.setPedido(pedido);
        pedidoPizzaRepository.save(pedidoPizza);

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
                throw new IllegalArgumentException(PEDIDO_NAO_ENCONTRADO_MSG + pedidoId);
            }

            // Criar um novo pagamento
            Pagamento pagamento = new Pagamento();
            pagamento.setFormasDePagamento(formDePagamento);
            pagamento.setPedido(pedido);
            pagamento.setPago(true);
            pagamentoRepository.save(pagamento);
            pedido.setPagamento(pagamento);

            // Calcular o valor total do pedido
            Double totalPedidoAmount = calculateTotalPedidoAmount(pedido);
            pedido.setValorTotal(totalPedidoAmount);

            // Definir o status do pedido como PAGO
            pedido.setStatus(Status.PAGO);
            return pedidoRepository.save(pedido);
        } catch (Exception e) {
            throw new IllegalArgumentException("Erro ao finalizar o pedido." + e);
        }
    }

    // Método para atualizar um pedido
    @Transactional
    public Pedidos updateOrder(Pedidos pedido) {

        if(pedido.getProdutos() != null)
            for(int i=0; i<pedido.getProdutos().size(); i++){
                pedido.getProdutos().get(i).setPedido(pedido);
            }

        if(pedido.getPizzas() != null)
            for(int i=0; i<pedido.getPizzas().size(); i++){
                pedido.getPizzas().get(i).setPedido(pedido);
            }

            return pedidoRepository.save(pedido);
    }

    // Método para calcular o valor total do pedido
    private double calculateTotalPedidoAmount(Pedidos pedido) {
        double productsTotal = calculateProductsTotal(pedido);
        double pizzasTotal = calculatePizzasTotal(pedido);
        double deliveryTotal = 0.0;
        if (pedido.getFormaDeEntrega() == FormaDeEntrega.ENTREGA){
            deliveryTotal = 8.0;
        }
        pedido.setValorPedido(productsTotal + pizzasTotal);
        pedido.setValorEntrega(deliveryTotal);
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
    public double calculatePedidoProdutoTotal(PedidoProduto pedidoProduto) {
        return pedidoProduto.getQtdePedida() * pedidoProduto.getProduto().getPreco();
    }

    // Método para calcular o total de um item do tipo PedidoPizza
    public double calculatePedidoPizzaTotal(PedidoPizza pedidoPizza) {
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
            throw new IllegalArgumentException(PEDIDO_NAO_ENCONTRADO_MSG
                    + pedidoProdutoDTO.getProduto().getId());
        }

        // Definir o pedido e o produto no objeto PedidoProduto
        pedidoProduto.setPedido(pedidos);
        pedidoProduto.setProduto(produtos);

        return pedidoProduto;
    }

    // Método para converter um objeto Pedidos em um DTO
    public PedidosDTO toPedidosDTO(Pedidos pedidos) {
        PedidosDTO pedidosDTO = modelMapper.map(pedidos, PedidosDTO.class);

        if (pedidos.getPagamento() != null) {
            PagamentoDTO pagamentoDTO = modelMapper.map(pedidos.getPagamento(), PagamentoDTO.class);
            pedidosDTO.setPagamentoDTO(pagamentoDTO);
        }

        return pedidosDTO;
    }

    public Pedidos toPedido(PedidosDTO pedidosDTO) {
        return  modelMapper.map(pedidosDTO, Pedidos.class);
    }

    // Método para converter um DTO em um objeto PedidoPizza
    public PedidoPizza toPedidoPizza(PedidoPizzaDTO pedidoPizzaDTO) {

        PedidoPizza pedidoPizza = modelMapper.map(pedidoPizzaDTO, PedidoPizza.class);

        // Encontrar a pizza pelo ID
        Pizzas pizzas = pizzaRepository.findById(pedidoPizzaDTO.getPizza().getId()).orElse(null);
        if (pizzas == null) {
            throw new IllegalArgumentException("Pizza não encontrada com o ID: " + pedidoPizzaDTO.getPizza().getId());
        }

        // Encontrar o pedido pelo ID
        Pedidos pedidos = pedidoRepository.findById(pedidoPizza.getPedido().getId()).orElse(null);
        if (pedidos == null) {
            throw new IllegalArgumentException(PEDIDO_NAO_ENCONTRADO_MSG);
        }

        // Mapear os sabores
        List<Sabores> sabores = new ArrayList<>();
        for (SaboresDTO saborDTO : pedidoPizzaDTO.getSabores()) {
            Sabores sabor = saboresRepository.findById(saborDTO.getId()).orElse(null);
            if (sabor == null) {
                throw new IllegalArgumentException("Sabor não encontrado com o ID: " + saborDTO.getId());
            }
            sabores.add(sabor);

        }

        // Definir o pedido, a pizza e os sabores no objeto PedidoPizza
        pedidoPizza.setPedido(pedidos);
        pedidoPizza.setPizza(pizzas);
        pedidoPizza.setSabores(sabores);

        return pedidoPizza;
    }


    public Pedidos removePedidoPizzaFromPedido(Long pedidoId, Long pedidoPizzaId) {
        Pedidos pedido = pedidoRepository.findById(pedidoId).orElse(null);

        if (pedido != null) {
            List<PedidoPizza> pedidoPizzas = pedido.getPizzas();
            pedidoPizzas.removeIf(pedidoPizza -> pedidoPizza.getId().equals(pedidoPizzaId));
            pedidoRepository.save(pedido);
            return pedido;
        }

        throw new IllegalArgumentException("Pedido not found");
    }



}
