package com.mensal.sliceCtrl.DTO;

import com.mensal.sliceCtrl.entity.Pizza;
import com.mensal.sliceCtrl.entity.Produtos;
import com.mensal.sliceCtrl.entity.enums.Status;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class PedidoDTO {

    private Long clienteId;

    private Long funcionarioId;

    private String codigo;

    private List<Produtos> produtos;

    private List<Pizza> pizzas;

    @NotNull(message = "O status do pedido é obrigatório")
    private Status status;

    @DecimalMin(value = "0.0", message = "O valor do pedido deve ser maior ou igual a 0")
    private BigDecimal valorPedido;

    @DecimalMin(value = "0.0", message = "O valor da entrega deve ser maior ou igual a 0")
    private BigDecimal valorEntrega;

    @DecimalMin(value = "0.0", message = "O valor total deve ser maior ou igual a 0")
    private BigDecimal valorTotal;

    private Long pagamentoId;

    private boolean forEntrega;

    private boolean forTakeaway;

    private boolean forDineIn;
}
