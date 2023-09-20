package com.mensal.slicectrl.ServiceTest;

import com.mensal.slicectrl.dto.ClientesDTO;
import com.mensal.slicectrl.dto.FuncionariosDTO;
import com.mensal.slicectrl.dto.PedidosDTO;
import com.mensal.slicectrl.entity.Clientes;
import com.mensal.slicectrl.entity.Funcionarios;
import com.mensal.slicectrl.entity.Pedidos;
import com.mensal.slicectrl.entity.enums.FormaDeEntrega;
import com.mensal.slicectrl.entity.enums.Status;
import com.mensal.slicectrl.repository.ClienteRepository;
import com.mensal.slicectrl.repository.FuncionarioRepository;
import com.mensal.slicectrl.repository.PedidoRepository;
import com.mensal.slicectrl.service.PedidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
    private ModelMapper modelMapper;

    @InjectMocks
    private PedidoService pedidoService;

    private List<Pedidos> pedidos = new ArrayList<>();
    Pedidos pedido = new Pedidos();
    Pedidos pedido1 = new Pedidos();

    Clientes cliente = new Clientes();
    Funcionarios funcionarios = new Funcionarios();
    Clientes cliente1 = new Clientes();

    @BeforeEach
    void setUp(){

        pedido.setId(1L);
        pedido.setStatus(Status.PAGO);
        pedido.setFormaDeEntrega(FormaDeEntrega.RETIRADA);
        cliente.setId(1L);
        pedido.setCliente(cliente);
        pedido.setFuncionario(funcionarios);

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


}
