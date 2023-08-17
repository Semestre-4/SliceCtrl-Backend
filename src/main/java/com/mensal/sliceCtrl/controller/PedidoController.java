package com.mensal.sliceCtrl.controller;

import com.mensal.sliceCtrl.DTO.PedidoPizzaDTO;
import com.mensal.sliceCtrl.DTO.PedidoProdutoDTO;
import com.mensal.sliceCtrl.DTO.PedidosDTO;
import com.mensal.sliceCtrl.entity.Pedidos;
import com.mensal.sliceCtrl.entity.enums.FormasDePagamento;
import com.mensal.sliceCtrl.entity.enums.Status;
import com.mensal.sliceCtrl.repository.PedidoRepository;
import com.mensal.sliceCtrl.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * Esta classe representa a controller para lidar com operações relacionadas a pedidos.
 */

@RestController
@RequestMapping("api/pedido")
public class PedidoController {

    @Autowired
    public PedidoService pedidoService;


    /**
     * Recupera um pedido pelo seu ID.
     *
     * @param id O ID do pedido a ser recuperado.
     * @return ResponseEntity contendo as informações do pedido, se encontrado, ou uma resposta de erro.
     */
    @GetMapping("/id/{id}")
    public ResponseEntity<?> getPedidoById(@PathVariable("id") Long id) {
        try {
            PedidosDTO pedidosDTO = pedidoService.findById(id);
            return ResponseEntity.ok(pedidosDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    /**
     * Recupera uma lista de todos os pedidos.
     *
     * @return ResponseEntity contendo a lista de todos os pedidos.
     */
    @GetMapping("/all")
    public ResponseEntity<List<PedidosDTO>> getAllPedidos() {
        List<PedidosDTO> pedidosDTOS = pedidoService.findAll();
        return ResponseEntity.ok(pedidosDTOS);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<PedidosDTO>> getPedidosByStatus(@PathVariable Status status) {
        return ResponseEntity.ok(pedidoService.findByStatus(status));
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<PedidosDTO>> getPedidosByCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(pedidoService.findByCliente_Id(clienteId));
    }

    @GetMapping("/funcionario/{funcionarioId}")
    public ResponseEntity<List<PedidosDTO>> getPedidosByFuncionario(@PathVariable Long funcionarioId) {
        return ResponseEntity.ok(pedidoService.findByFuncionario_Id(funcionarioId));
    }

    @GetMapping("/delivery")
    public ResponseEntity<List<PedidosDTO>> getPedidosForDelivery() {
        return ResponseEntity.ok(pedidoService.findByForEntrega());
    }

    @GetMapping("/takeaway")
    public ResponseEntity<List<PedidosDTO>> getPedidosForTakeaway() {
        return ResponseEntity.ok(pedidoService.findByForTakeaway());
    }

    @GetMapping("/dine-in")
    public ResponseEntity<List<PedidosDTO>> getPedidosForDineIn() {
        return ResponseEntity.ok(pedidoService.findByForDineIn());
    }

    @GetMapping("/pagamento-pending")
    public ResponseEntity<List<PedidosDTO>> getPedidosWithPagamentoPending() {
        return ResponseEntity.ok(pedidoService.findOrdersWithPendingPayments());
    }

    @PostMapping("/abrir/{clienteId}/{funcId}")
    public ResponseEntity<PedidosDTO> abrirPedido(@PathVariable("clienteId") Long clienteId,
                                               @PathVariable("funcId") Long funcId) {
        try {
            Pedidos pedido = new Pedidos();
            PedidosDTO savedPedido = pedidoService.iniciarPedido(clienteId, pedido,funcId);
            return ResponseEntity.ok(savedPedido);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/adicionar/produto/{pedidoId}")
    public ResponseEntity<?> addProdutoToPedido(
            @PathVariable Long pedidoId,
            @RequestBody PedidoProdutoDTO pedidoProdutoDTO) {
        try {
            Pedidos updatedPedido = pedidoService.addProdutoToPedido(pedidoId, pedidoProdutoDTO);
            return ResponseEntity.ok(updatedPedido);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/adicionar/pizza/{pedidoId}")
    public ResponseEntity<?> addPizzaPedido(
            @PathVariable Long pedidoId,
            @RequestBody PedidoPizzaDTO pedidoPizzaDTO) {
        try {
            Pedidos updatedPedido = pedidoService.addPizzaToPedido(pedidoId, pedidoPizzaDTO);
            return ResponseEntity.ok(updatedPedido);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/{pedidoId}/pagamento/{formDePagamento}")
    public ResponseEntity<Pedidos> finalizarPedido(@PathVariable Long pedidoId,
                                                   @PathVariable FormasDePagamento formDePagamento) {
        try {
            Pedidos pedido = pedidoService.efetuarPedido(pedidoId, formDePagamento);
            return new ResponseEntity<Pedidos>(pedido, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{pedidoId}")
    public ResponseEntity<Pedidos> updateOrderByUser(@PathVariable Long pedidoId) {
        Pedidos pedido = pedidoService.updateOrder(pedidoId);
        return new ResponseEntity<Pedidos>(pedido, HttpStatus.OK);
    }

}
