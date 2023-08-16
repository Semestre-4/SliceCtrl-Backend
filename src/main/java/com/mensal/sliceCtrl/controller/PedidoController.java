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

    /**
     * Recupera a lista de produtos de um pedido pelo ID do pedido.
     *
     * @param pedidoId O ID do pedido.
     * @return ResponseEntity contendo a lista de produtos do pedido, ou uma resposta de erro.
     */
    @GetMapping("/produtos/{pedidoId}")
    public ResponseEntity<List<ProdutosDTO>> getPedidoByProdutos(@PathVariable Long pedidoId) {
        PedidosDTO pedidosDTO = pedidoService.findById(pedidoId);
        if (pedidosDTO != null) {
            return ResponseEntity.ok(pedidosDTO.getProdutos());
        }
        return ResponseEntity.badRequest().body(Collections.emptyList());
    }

    @GetMapping("pizzas/{pedidoId}")
    public ResponseEntity<List<PizzasDTO>> getPedidoByPizzas(@PathVariable Long pedidoId) {
        PedidosDTO pedidosDTO = pedidoService.findById(pedidoId);
        if (pedidosDTO != null) {
            return ResponseEntity.ok(pedidosDTO.getPizzas());
        }
        return ResponseEntity.badRequest().body(Collections.emptyList());
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

    /**
     * Efetua um novo pedido.
     *
     * @param pedidosDTO Os dados do pedido a serem cadastrados.
     * @return ResponseEntity indicando o sucesso ou a falha do cadastro.
     */
    @PostMapping
    public ResponseEntity<String> efetuarPedido(@RequestBody @Validated PedidosDTO pedidosDTO) {
        try {
            Pedidos createdPedido = pedidoService.efetuarPedido(pedidosDTO);
            return ResponseEntity.ok(String.format("O pedido com código '%s' foi efetuado com sucesso.",
                    pedidosDTO.getCodigo()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ocorreu um erro durante o cadastro: " + e.getMessage());
        }
    }

    /**
     * Atualiza as informações de um pedido.
     *
     * @param id          O ID do pedido a ser editado.
     * @param pedidosDTO  Os dados atualizados do pedido.
     * @return ResponseEntity indicando o sucesso ou a falha da edição.
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> editarPedido(@PathVariable Long id,
                                               @RequestBody @Validated PedidosDTO pedidosDTO) {
        try {
            // Verifica se o pedido existe
            PedidosDTO existingPedidoDTO = pedidoService.findById(id);
            if (existingPedidoDTO == null) {
                return ResponseEntity.notFound().build();
            }

            // Verifica se o pedido já foi pago
            if (existingPedidoDTO.getStatus() == Status.PAGO) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).
                        body("Pagamento foi efetuado. Não é possível editar o pedido.");
            } else {
                Pedidos updatedPedido = pedidoService.updatePedido(id, pedidosDTO);
                return ResponseEntity.ok(String.format("O pedido com código '%s' foi atualizado com sucesso.",
                        pedidosDTO.getCodigo()));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ocorreu um erro durante a atualização: " + e.getMessage());
        }
    }

    /**
     * Exclui um pedido pelo seu ID.
     *
     * @param id O ID do pedido a ser excluído.
     * @return ResponseEntity indicando o sucesso ou a falha da exclusão.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirPedido(@PathVariable Long id) {
        try {
            pedidoService.deletePedido(id);
            return ResponseEntity.ok().body("Pedido excluído com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ocorreu um erro: " + e.getMessage());
        }
    }
}
