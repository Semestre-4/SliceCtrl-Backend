package com.mensal.slicectrl.ServiceTest;

import com.mensal.slicectrl.dto.*;
import com.mensal.slicectrl.entity.*;
import com.mensal.slicectrl.entity.enums.FormaDeEntrega;
import com.mensal.slicectrl.entity.enums.FormasDePagamento;
import com.mensal.slicectrl.entity.enums.Status;
import com.mensal.slicectrl.repository.*;
import com.mensal.slicectrl.service.PedidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private FuncionarioRepository funcionarioRepository;

    @Mock
    private ProdutoRepository produtoRepository;
    @Mock
    private  PedidoProdutoRepository pedidoProdutoRepository;

    @Mock
    private PedidoPizzaRepository pedidoPizzaDTORepository;

    @Mock
    private PagamentoRepository pagamentoRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PedidoService pedidoService;

    private final List<Pedidos> pedidos = new ArrayList<>();
    Pedidos pedido = new Pedidos();
    Pedidos pedido1 = new Pedidos();

    Clientes cliente = new Clientes();
    Funcionarios funcionarios = new Funcionarios();
    Clientes cliente1 = new Clientes();

    Pizzas pizza = new Pizzas();

    List<Sabores> sabores = new ArrayList<>();
    List<Ingredientes> inngredientes = new ArrayList<>();

    PedidoProduto pedidoProduto = new PedidoProduto();
    PedidoPizza pedidoPizza = new PedidoPizza();

    @BeforeEach
    void setUp(){

        inngredientes.add(new Ingredientes("nome",202));

        sabores.add(new Sabores("Sabor1", inngredientes));
        sabores.add(new Sabores("Sabor2", inngredientes));

        pedido.setId(1L);
        pedido.setStatus(Status.PENDENTE);
        pedido.setFormaDeEntrega(FormaDeEntrega.RETIRADA);
        cliente.setId(1L);
        pedido.setCliente(cliente);
        pedido.setFuncionario(funcionarios);
        pedido.setValorTotal(100.0);


        pedidoProduto.setPedido(pedido);
        pedidoProduto.setProduto(new Produtos());
        pedidoProduto.setQtdePedida(3);

        pedidoPizza.setPedido(pedido);
        pedidoPizza.setPizza(pizza);
        pedidoPizza.setSabores(sabores);

        pedido1.setId(2L);
        pedido1.setStatus(Status.PENDENTE);
        pedido.setFormaDeEntrega(FormaDeEntrega.RETIRADA);
        cliente1.setId(2L);
        pedido1.setCliente(cliente1);
        pedido1.setFuncionario(funcionarios);

        pedidos.add(pedido);
        pedidos.add(pedido1);

        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));
        when(pedidoRepository.findAll()).thenReturn(pedidos);
        when(pedidoRepository.findByCliente(1L)).thenReturn(pedidos);
        when(pedidoRepository.findByFunc(1L)).thenReturn(pedidos);
        when(modelMapper.map(pedido, PedidosDTO.class)).thenReturn(new PedidosDTO());
        when(modelMapper.map(cliente, ClientesDTO.class)).thenReturn(new ClientesDTO());
        when(modelMapper.map(funcionarios, FuncionariosDTO.class)).thenReturn(new FuncionariosDTO());
        when(pedidoProdutoRepository.save(pedidoProduto)).thenReturn(pedidoProduto);
        when(pedidoPizzaDTORepository.save(pedidoPizza)).thenReturn(pedidoPizza);
        when(pedidoRepository.save(pedido)).thenReturn(pedido);


    }

    @Test
    void testFindById_ValidId() {
        PedidosDTO result = pedidoService.findById(1L);
        assertNotNull(result);
    }

    @Test
    void testFindAll() {
        List<PedidosDTO> result = pedidoService.findAll();
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void testFindByStatus() {
        Status status = Status.PENDENTE;
        when(pedidoRepository.findByStatus(status)).thenReturn(pedidos);
        List<PedidosDTO> result = pedidoService.findByStatus(status);
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void testFindByFormaDeEntrega() {
        FormaDeEntrega formaDeEntrega = FormaDeEntrega.RETIRADA;
        when(pedidoRepository.findByformaDeEntrega(formaDeEntrega)).thenReturn(pedidos);
        List<PedidosDTO> result = pedidoService.findByformaDeEntrega(formaDeEntrega);
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void testFindByClienteId() {
        List<PedidosDTO> result = pedidoService.findByClienteId(1L);
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void testFindByFuncionarioId() {
        List<PedidosDTO> result = pedidoService.findByFuncionarioId(1L);
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void testFindOrdersWithPendingPayments() {
        when(pedidoRepository.findOrdersWithPendingPayments()).thenReturn(pedidos);
        List<PedidosDTO> result = pedidoService.findOrdersWithPendingPayments();
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void testIniciarPedido_ValidScenario() {
        // Arrange
        Long clienteId = 1L;
        Long funcId = 2L;
        FormaDeEntrega formaDeEntrega = FormaDeEntrega.RETIRADA;

        // Mock behavior of repository methods
        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(cliente));
        when(funcionarioRepository.findById(funcId)).thenReturn(Optional.of(funcionarios));
        when(pedidoRepository.findByClienteAndStatus(cliente, Status.PENDENTE)).thenReturn(new ArrayList<>());

        // Act
        PedidosDTO result = pedidoService.iniciarPedido(clienteId, pedido, funcId, formaDeEntrega);

        // Assert
        assertNotNull(result);
    }


    @Test
    void testIniciarPedido_CustomerOrEmployeeNotFound() {
        Pedidos order = new Pedidos();
        FormaDeEntrega deliveryMethod = FormaDeEntrega.ENTREGA;

        when(clienteRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(funcionarioRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> pedidoService.iniciarPedido(1L, order, 2L, deliveryMethod));
    }

    @Test
    void testIniciarPedido_PendingOrderExists() {
        List<Pedidos> pendingOrders = new ArrayList<>();
        Pedidos pendingOrder = new Pedidos();
        pendingOrder.setStatus(Status.PENDENTE);
        pendingOrders.add(pendingOrder);
        FormaDeEntrega deliveryMethod = FormaDeEntrega.RETIRADA;

        when(clienteRepository.findById(anyLong())).thenReturn(Optional.of(cliente));
        when(pedidoRepository.findByClienteAndStatus(cliente, Status.PENDENTE)).thenReturn(pendingOrders);

        assertThrows(IllegalArgumentException.class, () -> pedidoService.iniciarPedido(1L, pedido, 2L, deliveryMethod));
    }

    @Test
    void testAddProdutoToPedido_OrderNotFound() {
        when(pedidoRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> pedidoService.addProdutoToPedido(1L, new PedidoProdutoDTO()));
    }

    @Test
    void testAddProdutoToPedido_OrderIsPaid() {
        Pedidos order = new Pedidos();
        order.setStatus(Status.PAGO);
        when(pedidoRepository.findById(anyLong())).thenReturn(Optional.of(order));

        assertThrows(IllegalArgumentException.class, () -> pedidoService.addProdutoToPedido(1L, new PedidoProdutoDTO()));
    }

    @Test
    void testAddProdutoToPedido_ProductNotAvailable() {
        // Product not available scenario
        Pedidos order = new Pedidos();
        order.setStatus(Status.PENDENTE);
        Produtos product = new Produtos();
        product.setQtdeEstoque(0);
        ProdutosDTO productDTO = new ProdutosDTO();
        product.setQtdeEstoque(0);
        PedidoProdutoDTO pedidoProdutoDTO = new PedidoProdutoDTO();
        pedidoProdutoDTO.setProduto(productDTO);


        when(pedidoRepository.findById(anyLong())).thenReturn(Optional.of(order));
        when(produtoRepository.findById(anyLong())).thenReturn(Optional.of(product));

        assertThrows(IllegalArgumentException.class, () -> pedidoService.addProdutoToPedido(1L, pedidoProdutoDTO));
    }

    @Test
    void testEfetuarPedido() {
        // Mock data
        Long pedidoId = 1L;
        FormasDePagamento formDePagamento = FormasDePagamento.CREDITO;
        double totalAmount = 0.0;

        // Mock behavior for pedidoRepository
        Pedidos result = pedidoService.efetuarPedido(pedidoId, formDePagamento);

        // Assertions
        assertNotNull(result);
        assertEquals(Status.PAGO, result.getStatus());
        assertEquals(totalAmount, result.getValorTotal());
        assertTrue(result.getPagamento().isPago());
    }

    @Test
    void testUpdateOrderSuccess() {
        // Mock data
        Long pedidoId = 1L;
        Pedidos existingPedido = new Pedidos();
        existingPedido.setId(pedidoId);
        existingPedido.setStatus(Status.PENDENTE);

        when(pedidoRepository.findById(pedidoId)).thenReturn(Optional.of(existingPedido));
        when(pedidoRepository.save(existingPedido)).thenReturn(existingPedido);

        Pedidos result = pedidoService.updateOrder(pedidoId);

        // Assertions
        assertNotNull(result);
        assertEquals(pedidoId, result.getId());
        assertEquals(Status.PENDENTE, result.getStatus());

        // Verify that save method was called
        Mockito.verify(pedidoRepository, Mockito.times(1)).save(existingPedido);
    }

    @Test
    void testUpdateOrderNotFound() {
        // Mock data
        Long pedidoId = 2L;

        // Mock behavior for pedidoRepository
        when(pedidoRepository.findById(pedidoId)).thenReturn(Optional.empty());

        // Call the method to be tested and expect an exception
        assertThrows(IllegalArgumentException.class, () -> {
            pedidoService.updateOrder(pedidoId);
        });

        // Verify that save method was not called
        Mockito.verify(pedidoRepository, Mockito.never()).save(Mockito.any(Pedidos.class));
    }

    @Test
    public void testUpdateOrderNotAllowedStatus() {
        // Mock data
        Long pedidoId = 3L;
        Pedidos existingPedido = new Pedidos();
        existingPedido.setId(pedidoId);
        existingPedido.setStatus(Status.PAGO);

        when(pedidoRepository.findById(pedidoId)).thenReturn(Optional.of(existingPedido));
        assertThrows(IllegalArgumentException.class, () -> {
            pedidoService.updateOrder(pedidoId);
        });
        Mockito.verify(pedidoRepository, Mockito.never()).save(Mockito.any(Pedidos.class));
    }


}
