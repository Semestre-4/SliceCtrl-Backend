package com.mensal.sliceCtrl.DTO;

import com.mensal.sliceCtrl.entity.Cliente;
import com.mensal.sliceCtrl.entity.Funcionario;
import com.mensal.sliceCtrl.entity.Pagamento;
import com.mensal.sliceCtrl.entity.enums.Status;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class PedidoDTO extends AbstractEntityDTO{

    private Cliente clienteDTO;

    private Funcionario funcionarioDTO;

    private String codigo;

    private List<ProdutosDTO> produtos;

    private List<PizzaDTO> pizzas;

    @NotNull(message = "O status do pedido é obrigatório")
    private Status status;

    @DecimalMin(value = "0.0", message = "O valor do pedido deve ser maior ou igual a 0")
    private BigDecimal valorPedido;

    @DecimalMin(value = "0.0", message = "O valor da entrega deve ser maior ou igual a 0")
    private BigDecimal valorEntrega;

    @DecimalMin(value = "0.0", message = "O valor total deve ser maior ou igual a 0")
    private BigDecimal valorTotal;

    private Pagamento pagamento;

    private boolean forEntrega;

    private boolean forTakeaway;

    private boolean forDineIn;
}
