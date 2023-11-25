package com.mensal.slicectrl.controller;

import com.mensal.slicectrl.dto.PedidoPizzaDTO;
import com.mensal.slicectrl.dto.PedidoProdutoDTO;
import com.mensal.slicectrl.dto.PedidosDTO;
import com.mensal.slicectrl.entity.Pedidos;
import com.mensal.slicectrl.entity.Produtos;
import com.mensal.slicectrl.entity.enums.FormaDeEntrega;
import com.mensal.slicectrl.entity.enums.FormasDePagamento;
import com.mensal.slicectrl.entity.enums.Status;
import com.mensal.slicectrl.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Esta classe representa a controller para lidar com operações relacionadas a pedidos.
 */

@RestController
@RequestMapping("api/pedido")
@CrossOrigin("http://localhost:4200")
public class PedidoController {

    @Autowired
    public PedidoService pedidoService;

    @GetMapping("/id/{id}")
    public ResponseEntity<PedidosDTO> getPedidoById(@PathVariable("id") Long id) {
            PedidosDTO pedidosDTO = pedidoService.findById(id);
            return ResponseEntity.ok(pedidosDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PedidosDTO>> getAllPedidos() {
        List<PedidosDTO> pedidosDTOS = pedidoService.findAll();
        return ResponseEntity.ok(pedidosDTOS);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<PedidosDTO>> getPedidosByStatus(@PathVariable Status status) {
        return ResponseEntity.ok(pedidoService.findByStatus(status));
    }

    @GetMapping("/formaDeEntrega/{formaDeEntrega}")
    public ResponseEntity<List<PedidosDTO>> getPedidosByformaDeEntrega(@PathVariable FormaDeEntrega formaDeEntrega) {
        return ResponseEntity.ok(pedidoService.findByformaDeEntrega(formaDeEntrega));
    }

    @GetMapping("/countByFormaDePagamento/{formaDePagamento}")
    public int countPedidosByFormaDePagamento(@PathVariable FormasDePagamento formaDePagamento) {
        return pedidoService.countPedidosByFormaDePagamento(formaDePagamento);
    }

    @GetMapping("/mostUsedSabores")
    public List<Object[]> getMostUsedSabores() {
        return pedidoService.findMostUsedSabores();
    }

    @GetMapping("/mostUsedProducts")
    public List<Produtos> getMostUsedProducts() {
        return pedidoService.findMostUsedProducts();
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<PedidosDTO>> getPedidosByCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(pedidoService.findByClienteId(clienteId));
    }

    @GetMapping("/funcionario/{funcionarioId}")
    public ResponseEntity<List<PedidosDTO>> getPedidosByFuncionario(@PathVariable Long funcionarioId) {
        return ResponseEntity.ok(pedidoService.findByFuncionarioId(funcionarioId));
    }

    @GetMapping("/pagamento-pending")
    public ResponseEntity<List<PedidosDTO>> getPedidosWithPagamentoPending() {
        return ResponseEntity.ok(pedidoService.findOrdersWithPendingPayments());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'FUNCIONARIO')")
    @PostMapping("/abrir/{clienteId}/{funcId}/{formaDeEntrega}")
    public ResponseEntity<PedidosDTO> abrirPedido(@PathVariable("clienteId") Long clienteId,
                                                  @PathVariable("funcId") Long funcId,
                                                  @PathVariable("formaDeEntrega") FormaDeEntrega formaDeEntrega){
        try {
            Pedidos pedido = new Pedidos();
            PedidosDTO savedPedido = pedidoService.iniciarPedido(clienteId, pedido,funcId,formaDeEntrega);
            return  ResponseEntity.ok(savedPedido);
        } catch (Exception e) {
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'FUNCIONARIO')")
    @PutMapping("/adicionar/produto/{pedidoId}")
    public ResponseEntity<String> addProdutoToPedido(
            @PathVariable Long pedidoId,
            @RequestBody PedidoProdutoDTO pedidoProdutoDTO) {
        try {
             pedidoService.addProdutoToPedido(pedidoId, pedidoProdutoDTO);
            return ResponseEntity.ok(String.format("Produto [%s] adicionado com sucesso",
                    pedidoProdutoDTO.getProduto().getNomeProduto()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'FUNCIONARIO')")
    @PutMapping("/adicionar/pizza/{pedidoId}")
    public ResponseEntity<String> addPizzaPedido(
            @PathVariable Long pedidoId,
            @RequestBody PedidoPizzaDTO pedidoPizzaDTO) {
        try {
            pedidoService.addPizzaToPedido(pedidoId, pedidoPizzaDTO);
            return ResponseEntity.ok("Pizza adicionada com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'FUNCIONARIO')")
    @PutMapping("/{pedidoId}/pagamento/{formDePagamento}")
    public ResponseEntity<Pedidos> finalizarPedido(@PathVariable Long pedidoId,
                                                   @PathVariable FormasDePagamento formDePagamento) {
        try {
            Pedidos pedido = pedidoService.efetuarPedido(pedidoId, formDePagamento);
            return new ResponseEntity<>(pedido, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'FUNCIONARIO')")
    @PutMapping
    public ResponseEntity<Pedidos> updateOrderByUser(@RequestBody @Validated Pedidos pedido) {
     try{
        Pedidos pedidoabc = pedidoService.updateOrder(pedido);
        return new ResponseEntity<>(pedidoabc, HttpStatus.OK);
    } catch (Exception e) {
         System.out.println(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'FUNCIONARIO')")
    @PutMapping("/{pedidoId}/remover-pedido-pizza/{pedidoPizzaId}")
    public ResponseEntity<Pedidos> removePedidoPizzaFromPedido(@PathVariable Long pedidoId, @PathVariable Long pedidoPizzaId) {
        try {
            Pedidos pedido = pedidoService.removePedidoPizzaFromPedido(pedidoId, pedidoPizzaId);
            return new ResponseEntity<>(pedido, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
