package com.mensal.sliceCtrl.controller;

import com.mensal.sliceCtrl.DTO.PedidoPizzaDTO;
import com.mensal.sliceCtrl.DTO.PedidoProdutoDTO;
import com.mensal.sliceCtrl.DTO.PedidosDTO;
import com.mensal.sliceCtrl.entity.Pedidos;
import com.mensal.sliceCtrl.entity.enums.FormasDePagamento;
import com.mensal.sliceCtrl.entity.enums.Status;
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

    @PostMapping("/cliente/{clienteId}/pagamento/{formDePagamento}")
    public ResponseEntity<Pedidos> efetuarPedido(@PathVariable Long clienteId,
                                                    @PathVariable FormasDePagamento formDePagamento) {
        Pedidos pedido = pedidoService.efetuarPedido(clienteId, formDePagamento);
        return new ResponseEntity<Pedidos>(pedido, HttpStatus.CREATED);
    }

    @PutMapping("/cliente/{clienteId}/pedido/{pedidoId}/status/{status}")
    public ResponseEntity<Pedidos> updateOrderByUser(@PathVariable Long clienteId,
                                                        @PathVariable Long pedidoId,
                                                        @PathVariable Status status) {
        Pedidos pedido = pedidoService.updateOrder(clienteId, pedidoId, status);
        return new ResponseEntity<Pedidos>(pedido, HttpStatus.OK);
    }

}
