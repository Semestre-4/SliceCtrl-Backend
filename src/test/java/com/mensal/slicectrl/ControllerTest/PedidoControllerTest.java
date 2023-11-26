package com.mensal.slicectrl.ControllerTest;

import com.mensal.slicectrl.controller.PedidoController;
import com.mensal.slicectrl.dto.*;
import com.mensal.slicectrl.entity.Pedidos;
import com.mensal.slicectrl.entity.enums.FormaDeEntrega;
import com.mensal.slicectrl.entity.enums.FormasDePagamento;
import com.mensal.slicectrl.entity.enums.Status;
import com.mensal.slicectrl.service.PedidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.fail;

@SpringBootTest
class PedidoControllerTest {

    @InjectMocks
    private PedidoController pedidoController;

    @Mock
    private PedidoService pedidoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPedidoById() {
        Long pedidoId = 1L;
        PedidosDTO pedidosDTO = new PedidosDTO();
        pedidosDTO.setId(pedidoId);

        when(pedidoService.findById(pedidoId)).thenReturn(pedidosDTO);

        ResponseEntity<PedidosDTO> response = pedidoController.getPedidoById(pedidoId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pedidosDTO, response.getBody());

        verify(pedidoService, times(1)).findById(pedidoId);
    }


    @Test
    void testGetAllPedidos() {
        // Criar objetos de amostra PedidosDTO
        PedidosDTO pedido1 = new PedidosDTO();
        pedido1.setId(1L);
        pedido1.setCliente(new ClientesDTO());

        // Criar uma lista de objetos de amostra PedidosDTO
        List<PedidosDTO> pedidosDTOList = new ArrayList<>();
        pedidosDTOList.add(pedido1);

        // Simular o método de serviço para retornar a lista
        when(pedidoService.findAll()).thenReturn(pedidosDTOList);

        // Realizar o teste
        ResponseEntity<List<PedidosDTO>> response = pedidoController.getAllPedidos();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pedidosDTOList, response.getBody());

        verify(pedidoService, times(1)).findAll();
    }

    @Test
    void testGetPedidosByStatus() {
        Status status = Status.PENDENTE;
        List<PedidosDTO> pedidosDTOList = new ArrayList<>();

        // Adicionando um objeto PedidosDTO de amostra à lista
        PedidosDTO samplePedido = new PedidosDTO();
        samplePedido.setId(1L);
        samplePedido.setStatus(Status.PENDENTE);
        pedidosDTOList.add(samplePedido);

        when(pedidoService.findByStatus(status)).thenReturn(pedidosDTOList);

        ResponseEntity<List<PedidosDTO>> response = pedidoController.getPedidosByStatus(status);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pedidosDTOList, response.getBody());

        verify(pedidoService, times(1)).findByStatus(status);
    }

    @Test
    void testGetPedidosByFormaDeEntrega() {
        FormaDeEntrega formaDeEntrega = FormaDeEntrega.RETIRADA;
        List<PedidosDTO> pedidosDTOList = new ArrayList<>();

        // Adicionando um objeto PedidosDTO de amostra à lista
        PedidosDTO samplePedido = new PedidosDTO();
        samplePedido.setId(1L);
        samplePedido.setFormaDeEntrega(FormaDeEntrega.RETIRADA);
        pedidosDTOList.add(samplePedido);

        when(pedidoService.findByformaDeEntrega(formaDeEntrega)).thenReturn(pedidosDTOList);

        ResponseEntity<List<PedidosDTO>> response = pedidoController.getPedidosByformaDeEntrega(formaDeEntrega);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pedidosDTOList, response.getBody());

        verify(pedidoService, times(1)).findByformaDeEntrega(formaDeEntrega);
    }

    @Test
    void testGetPedidosByCliente() {
        Long clienteId = 1L;
        List<PedidosDTO> pedidosDTOList = new ArrayList<>();

        // Adicionando um objeto PedidosDTO de amostra à lista
        PedidosDTO samplePedido = new PedidosDTO();
        samplePedido.setId(1L);
        samplePedido.setCliente(new ClientesDTO());
        pedidosDTOList.add(samplePedido);

        when(pedidoService.findByClienteId(clienteId)).thenReturn(pedidosDTOList);

        ResponseEntity<List<PedidosDTO>> response = pedidoController.getPedidosByCliente(clienteId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pedidosDTOList, response.getBody());

        verify(pedidoService, times(1)).findByClienteId(clienteId);
    }

    @Test
    void testGetPedidosByFuncionario() {
        Long funcionarioId = 2L;
        List<PedidosDTO> pedidosDTOList = new ArrayList<>();

        // Adicionando um objeto PedidosDTO de amostra à lista
        PedidosDTO samplePedido = new PedidosDTO();
        samplePedido.setId(2L);
        samplePedido.setUsuario(new UsuarioDTO());
        pedidosDTOList.add(samplePedido);

        when(pedidoService.findByFuncionarioId(funcionarioId)).thenReturn(pedidosDTOList);

        ResponseEntity<List<PedidosDTO>> response = pedidoController.getPedidosByFuncionario(funcionarioId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pedidosDTOList, response.getBody());

        verify(pedidoService, times(1)).findByFuncionarioId(funcionarioId);
    }

    @Test
    void testGetPedidosWithPagamentoPending() {
        List<PedidosDTO> pedidosDTOList = new ArrayList<>(); // Criar uma lista de objetos PedidosDTO de amostra

        when(pedidoService.findOrdersWithPendingPayments()).thenReturn(pedidosDTOList);

        ResponseEntity<List<PedidosDTO>> response = pedidoController.getPedidosWithPagamentoPending();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pedidosDTOList, response.getBody());

        verify(pedidoService, times(1)).findOrdersWithPendingPayments();
    }

    @Test
    void testAbrirPedidoWithException() {
        Long clienteId = 1L;
        Long funcId = 2L;
        FormaDeEntrega formaDeEntrega = FormaDeEntrega.RETIRADA;

        // Simular o comportamento do pedidoService.iniciarPedido para lançar uma exceção
        when(pedidoService.iniciarPedido(
                eq(clienteId),
                any(Pedidos.class),
                eq(funcId),
                eq(formaDeEntrega)
        )).thenThrow(new RuntimeException("Mensagem de exceção de amostra"));

        ResponseEntity<PedidosDTO> response = pedidoController.abrirPedido(clienteId, funcId, formaDeEntrega);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        verify(pedidoService, times(1)).iniciarPedido(
                eq(clienteId),
                any(Pedidos.class),
                eq(funcId),
                eq(formaDeEntrega)
        );
    }

    @Test
    void testAbrirPedido() {
        Long clienteId = 1L;
        Long funcId = 2L;
        FormaDeEntrega formaDeEntrega = FormaDeEntrega.RETIRADA;

        // Configurar quaisquer propriedades necessárias do objeto pedido

        // Criar um objeto PedidosDTO de amostra
        PedidosDTO pedidosDTO = new PedidosDTO();
        // Configurar quaisquer propriedades necessárias do objeto pedidosDTO

        when(pedidoService.iniciarPedido(
                eq(clienteId),
                any(Pedidos.class),
                eq(funcId),
                eq(formaDeEntrega)
        )).thenReturn(pedidosDTO);

        ResponseEntity<PedidosDTO> response = pedidoController.abrirPedido(clienteId, funcId, formaDeEntrega);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        // Você pode realizar mais asserções no corpo da resposta ou em outros aspectos da resposta

        verify(pedidoService, times(1)).iniciarPedido(
                eq(clienteId),
                any(Pedidos.class),
                eq(funcId),
                eq(formaDeEntrega)
        );
    }

    @Test
    void testAddProdutoToPedido() {
        Long pedidoId = 1L;
        PedidoProdutoDTO pedidoProdutoDTO = new PedidoProdutoDTO();

        // Adicionar dados de amostra ao PedidoProdutoDTO
        pedidoProdutoDTO.setProduto(new ProdutosDTO()); // Configurar um ProdutosDTO de amostra
        pedidoProdutoDTO.setPedido(new PedidosDTO()); // Configurar um PedidosDTO de amostra
        pedidoProdutoDTO.setQtdePedida(2);

        try {
            pedidoController.addProdutoToPedido(pedidoId, pedidoProdutoDTO);
        } catch (Exception e) {
            fail("A exceção não deve ser lançada para uma adição de produto bem-sucedida.");
        }
    }

    @Test
    void testAddProdutoToPedidoWithException() {
        Long pedidoId = 1L;
        PedidoProdutoDTO pedidoProdutoDTO = new PedidoProdutoDTO();
        pedidoProdutoDTO.setProduto(new ProdutosDTO()); // Configurar um DTO de produto válido

        // Simular o comportamento de pedidoService.addProdutoToPedido para lançar uma exceção
        when(pedidoService.addProdutoToPedido(eq(pedidoId), any(PedidoProdutoDTO.class)))
                .thenThrow(new RuntimeException("Mensagem de exceção de amostra"));

        ResponseEntity<String> response = pedidoController.addProdutoToPedido(pedidoId, pedidoProdutoDTO);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Mensagem de exceção de amostra", response.getBody());

        verify(pedidoService, times(1)).addProdutoToPedido(eq(pedidoId), any(PedidoProdutoDTO.class));
    }
    @Test
    void testAddPizzaPedido() {
        Long pedidoId = 1L;
        PedidoPizzaDTO pedidoPizzaDTO = new PedidoPizzaDTO();

        // Adicionar dados de amostra ao PedidoPizzaDTO
        pedidoPizzaDTO.setPizza(new PizzasDTO()); // Configurar um PizzasDTO de amostra
        pedidoPizzaDTO.setPedido(new PedidosDTO()); // Configurar um PedidosDTO de amostra
        pedidoPizzaDTO.setQtdePedida(1);
        pedidoPizzaDTO.setObservacao("Extra queijo");

        try {
            pedidoController.addPizzaPedido(pedidoId, pedidoPizzaDTO);
        } catch (Exception e) {
            fail("A exceção não deve ser lançada para uma adição de pizza bem-sucedida.");
        }
        // Adicionar asserções para uma adição bem-sucedida
    }

    @Test
    void testAddPizzaToPedidoWithException() {
        Long pedidoId = 1L;
        PedidoPizzaDTO pedidoPizzaDTO = new PedidoPizzaDTO();
        pedidoPizzaDTO.setPizza(new PizzasDTO()); // Configurar um DTO de pizza válido

        // Simular o comportamento de pedidoService.addPizzaToPedido para lançar uma exceção
        when(pedidoService.addPizzaToPedido(eq(pedidoId), any(PedidoPizzaDTO.class)))
                .thenThrow(new RuntimeException("Mensagem de exceção de amostra"));

        ResponseEntity<String> response = pedidoController.addPizzaPedido(pedidoId, pedidoPizzaDTO);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Mensagem de exceção de amostra", response.getBody());

        verify(pedidoService, times(1)).addPizzaToPedido(eq(pedidoId), any(PedidoPizzaDTO.class));
    }

    @Test
    void testFinalizarPedido() {
        Long pedidoId = 1L;

        // Criar um objeto Pedidos de amostra
        Pedidos pedido = new Pedidos();
        pedido.setId(pedidoId);

        // Criar um objeto PagamentoDTO de amostra
        PagamentoDTO pagamentoDTO = new PagamentoDTO();
        pagamentoDTO.setFormasDePagamento(FormasDePagamento.CREDITO); // Configurar a formaDePagamento

        // Definir o PagamentoDTO no PedidosDTO
        PedidosDTO pedidosDTO = new PedidosDTO();
        pedidosDTO.setId(pedidoId);
        pedidosDTO.setPagamentoDTO(pagamentoDTO);

        when(pedidoService.efetuarPedido(pedidoId, FormasDePagamento.CREDITO)).thenReturn(pedido);

        ResponseEntity<Pedidos> response = pedidoController.finalizarPedido(pedidoId, FormasDePagamento.CREDITO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(pedido, response.getBody());

        verify(pedidoService, times(1)).efetuarPedido(pedidoId, FormasDePagamento.CREDITO);
    }

    @Test
    void testFinalizarPedidoWithException() {
        Long pedidoId = 1L;
        FormasDePagamento formDePagamento = FormasDePagamento.CREDITO;

        when(pedidoService.efetuarPedido(pedidoId, formDePagamento))
                .thenThrow(new RuntimeException("Mensagem de exceção de amostra"));

        ResponseEntity<Pedidos> response = pedidoController.finalizarPedido(pedidoId, formDePagamento);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody()); // Verificar que o corpo da resposta é nulo

        verify(pedidoService, times(1)).efetuarPedido(pedidoId, formDePagamento);
    }

//    @Test
//    void testUpdateOrderByUser() {
//        Long pedidoId = 1L;
//
//        // Criar um objeto Pedidos de amostra
//        Pedidos pedido = new Pedidos();
//        pedido.setId(pedidoId);
//
//        when(pedidoService.updateOrder(pedidoId)).thenReturn(pedido);
//
//        ResponseEntity<Pedidos> response = pedidoController.updateOrderByUser(pedidoId);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(pedido, response.getBody());
//
//        verify(pedidoService, times(1)).updateOrder(pedidoId);
//    }
}
