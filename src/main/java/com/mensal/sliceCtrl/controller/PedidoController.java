package com.mensal.sliceCtrl.controller;

import com.mensal.sliceCtrl.DTO.PedidoPizzaDTO;
import com.mensal.sliceCtrl.DTO.PedidoProdutoDTO;
import com.mensal.sliceCtrl.DTO.PedidosDTO;
import com.mensal.sliceCtrl.entity.Pedidos;
import com.mensal.sliceCtrl.entity.enums.FormaDeEntrega;
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
            return ResponseEntity.badRequest().body(e.getMessage());
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

    // Métodos semelhantes para recuperar pedidos por status, cliente, funcionário e tipo de entrega

    @GetMapping("/status/{status}")
    public ResponseEntity<List<PedidosDTO>> getPedidosByStatus(@PathVariable Status status) {
        return ResponseEntity.ok(pedidoService.findByStatus(status));
    }

    @GetMapping("/formaDeEntrega/{formaDeEntrega}")
    public ResponseEntity<List<PedidosDTO>> getPedidosByformaDeEntrega(@PathVariable FormaDeEntrega formaDeEntrega) {
        return ResponseEntity.ok(pedidoService.findByformaDeEntrega(formaDeEntrega));
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<PedidosDTO>> getPedidosByCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(pedidoService.findByCliente_Id(clienteId));
    }

    @GetMapping("/funcionario/{funcionarioId}")
    public ResponseEntity<List<PedidosDTO>> getPedidosByFuncionario(@PathVariable Long funcionarioId) {
        return ResponseEntity.ok(pedidoService.findByFuncionario_Id(funcionarioId));
    }

    @GetMapping("/pagamento-pending")
    public ResponseEntity<List<PedidosDTO>> getPedidosWithPagamentoPending() {
        return ResponseEntity.ok(pedidoService.findOrdersWithPendingPayments());
    }

    /**
     * Abre um novo pedido para um cliente específico.
     *
     * @param clienteId O ID do cliente para o qual o pedido será aberto.
     * @param funcId    O ID do funcionário responsável pelo pedido.
     * @return ResponseEntity contendo as informações do pedido aberto ou uma resposta de erro.
     */
    @PostMapping("/abrir/{clienteId}/{funcId}/{formaDeEntrega}")
    public ResponseEntity<String> abrirPedido(@PathVariable("clienteId") Long clienteId,
                                                  @PathVariable("funcId") Long funcId,
                                                  @PathVariable("formaDeEntrega") FormaDeEntrega formaDeEntrega){
        try {
            Pedidos pedido = new Pedidos();
            PedidosDTO savedPedido = pedidoService.iniciarPedido(clienteId, pedido,funcId,formaDeEntrega);
            return ResponseEntity.ok(String.format("%s",savedPedido));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Adiciona um produto a um pedido existente.
     *
     * @param pedidoId         O ID do pedido ao qual o produto será adicionado.
     * @param pedidoProdutoDTO O DTO contendo as informações do produto a ser adicionado.
     * @return ResponseEntity contendo as informações do pedido atualizado ou uma resposta de erro.
     */
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

    /**
     * Adiciona uma pizza a um pedido existente.
     *
     * @param pedidoId       O ID do pedido ao qual a pizza será adicionada.
     * @param pedidoPizzaDTO O DTO contendo as informações da pizza a ser adicionada.
     * @return ResponseEntity contendo as informações do pedido atualizado ou uma resposta de erro.
     */
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

    /**
     * Finaliza um pedido realizando o pagamento.
     *
     * @param pedidoId         O ID do pedido a ser finalizado.
     * @param formDePagamento  O tipo de forma de pagamento a ser usado.
     * @return ResponseEntity contendo as informações do pedido finalizado ou uma resposta de erro.
     */
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

    /**
     * Atualiza um pedido existente.
     *
     * @param pedidoId O ID do pedido a ser atualizado.
     * @return ResponseEntity contendo as informações do pedido atualizado ou uma resposta de erro.
     */
    @PutMapping("/{pedidoId}")
    public ResponseEntity<Pedidos> updateOrderByUser(@PathVariable Long pedidoId) {
        Pedidos pedido = pedidoService.updateOrder(pedidoId);
        return new ResponseEntity<Pedidos>(pedido, HttpStatus.OK);
    }


}
