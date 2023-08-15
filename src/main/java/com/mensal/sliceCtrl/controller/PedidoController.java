package com.mensal.sliceCtrl.controller;

import com.mensal.sliceCtrl.DTO.PedidosDTO;
import com.mensal.sliceCtrl.DTO.PizzasDTO;
import com.mensal.sliceCtrl.DTO.ProdutosDTO;
import com.mensal.sliceCtrl.entity.Pedidos;
import com.mensal.sliceCtrl.entity.enums.Status;
import com.mensal.sliceCtrl.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("api/pedido")
public class PedidoController {

    @Autowired
    public PedidoService pedidoService;

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getPedidoById(@PathVariable("id") Long id) {
        try {
            PedidosDTO pedidosDTO = pedidoService.findById(id);
            return ResponseEntity.ok(pedidosDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<PedidosDTO>> getAllPedidos() {
        List<PedidosDTO> pedidosDTOS = pedidoService.findAll();
        return ResponseEntity.ok(pedidosDTOS);
    }

    @GetMapping("/{pedidoId}/produtos")
    public List<ProdutosDTO> getPedidoProdutos(@PathVariable Long pedidoId) {
        PedidosDTO pedidosDTO = pedidoService.findById(pedidoId);
        if (pedidosDTO != null) {
            return pedidosDTO.getProdutos();
        }
        return Collections.emptyList();
    }

    @GetMapping("/{pedidoId}/pizzas")
    public List<PizzasDTO> getPedidoPizza(@PathVariable Long pedidoId) {
        PedidosDTO pedidosDTO = pedidoService.findById(pedidoId);
        if (pedidosDTO != null) {
            return pedidosDTO.getPizzas();
        }
        return Collections.emptyList();
    }

    @GetMapping("/status/{status}")
    public List<PedidosDTO> getPedidosByStatus(@PathVariable Status status) {
        return pedidoService.findByStatus(status);
    }

    @GetMapping("/cliente/{clienteId}")
    public List<PedidosDTO> getPedidosByCliente(@PathVariable Long clienteId) {
        return pedidoService.findByCliente_Id(clienteId);
    }

    @GetMapping("/funcionario/{funcionarioId}")
    public List<PedidosDTO> getPedidosByFuncionario(@PathVariable Long funcionarioId) {
        return pedidoService.findByFuncionario_Id(funcionarioId);
    }

    @GetMapping("/delivery")
    public List<PedidosDTO> getPedidosForDelivery() {
        return pedidoService.findByForEntrega();
    }

    @GetMapping("/takeaway")
    public List<PedidosDTO> getPedidosForTakeaway() {
        return pedidoService.findByForTakeaway();
    }

    @GetMapping("/dine-in")
    public List<PedidosDTO> getPedidosForDineIn() {
        return pedidoService.findByForDineIn();
    }

    @GetMapping("/pagamento-pending")
    public List<PedidosDTO> getPedidosWithPagamentoPending() {
        return pedidoService.findOrdersWithPendingPayments();
    }

    @PostMapping
    public ResponseEntity<?> efetuarPedido(@RequestBody @Validated PedidosDTO pedidosDTO) {
        try {
            Pedidos createdPedido = pedidoService.efetuarPedido(pedidosDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPedido);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePedido(@PathVariable Long id, @RequestBody PedidosDTO pedidosDTO) {
        try {
            PedidosDTO existingPedidoDTO = pedidoService.findById(id);
            if (existingPedidoDTO == null) {
                return ResponseEntity.notFound().build();
            }

            if (existingPedidoDTO.getStatus() == Status.PAGO) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Payment has been done. Cannot edit the pedido.");
            } else {
                Pedidos updatedPedido = pedidoService.updatePedido(id, pedidosDTO);
                return ResponseEntity.ok(updatedPedido);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePedido(@PathVariable Long id) {
        try {
            pedidoService.deletePedido(id);
            return ResponseEntity.ok().body("Pedido excluido com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
