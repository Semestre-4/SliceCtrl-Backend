package com.mensal.slicectrl.controller;

import com.mensal.slicectrl.dto.PedidoPizzaDTO;
import com.mensal.slicectrl.dto.PedidoProdutoDTO;
import com.mensal.slicectrl.dto.PedidosDTO;
import com.mensal.slicectrl.entity.Pedidos;
import com.mensal.slicectrl.entity.enums.FormaDeEntrega;
import com.mensal.slicectrl.entity.enums.FormasDePagamento;
import com.mensal.slicectrl.entity.enums.Status;
import com.mensal.slicectrl.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<PedidosDTO> getPedidoById(@PathVariable("id") Long id) {
            PedidosDTO pedidosDTO = pedidoService.findById(id);
            return ResponseEntity.ok(pedidosDTO);
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

    /**
     * Abre um novo pedido para um cliente específico.
     *
     * @param clienteId O ID do cliente para o qual o pedido será aberto.
     * @param funcId    O ID do funcionário responsável pelo pedido.
     * @return ResponseEntity contendo as informações do pedido aberto ou uma resposta de erro.
     */
    @PostMapping("/abrir/{clienteId}/{funcId}/{formaDeEntrega}")
    public ResponseEntity<PedidosDTO> abrirPedido(@PathVariable("clienteId") Long clienteId,
                                                  @PathVariable("funcId") Long funcId,
                                                  @PathVariable("formaDeEntrega") FormaDeEntrega formaDeEntrega){
        try {
            Pedidos pedido = new Pedidos();
            PedidosDTO savedPedido = pedidoService.iniciarPedido(clienteId, pedido,funcId,formaDeEntrega);
//            return ResponseEntity.ok(String.format("%s",savedPedido));
            return  ResponseEntity.ok(savedPedido);
        } catch (Exception e) {
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
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

    /**
     * Adiciona uma pizza a um pedido existente.
     *
     * @param pedidoId       O ID do pedido ao qual a pizza será adicionada.
     * @param pedidoPizzaDTO O DTO contendo as informações da pizza a ser adicionada.
     * @return ResponseEntity contendo as informações do pedido atualizado ou uma resposta de erro.
     */
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
            return new ResponseEntity<>(pedido, HttpStatus.CREATED);
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
        return new ResponseEntity<>(pedido, HttpStatus.OK);
    }

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
