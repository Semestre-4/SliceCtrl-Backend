package com.mensal.sliceCtrl.DTO;

import com.mensal.sliceCtrl.entity.Clientes;
import com.mensal.sliceCtrl.entity.Funcionarios;
import com.mensal.sliceCtrl.entity.enums.FormasDePagamento;
import com.mensal.sliceCtrl.entity.enums.Status;
import jakarta.persistence.Column;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class PedidosDTO extends AbstractEntityDTO {

    @NotNull(message = "É obrigatorio informar o cliente associado")
    private Clientes clienteId;
    @NotNull(message = "É obrigatorio informar o funcionario associado")
    private Funcionarios funcionarioId;
    private List<ProdutosDTO> produtos;
    private List<PizzasDTO> pizzas;
    @NotNull(message = "O status do pedido é obrigatório")
    private Status status;

    @DecimalMin(value = "0.0", message = "O valor do pedido deve ser maior ou igual a 0")
    private BigDecimal valorPedido;

    @DecimalMin(value = "0.0", message = "O valor da entrega deve ser maior ou igual a 0")
    private BigDecimal valorEntrega;

    @DecimalMin(value = "0.0", message = "O valor total deve ser maior ou igual a 0")
    private BigDecimal valorTotal;

    @NotNull(message = "A forma de pagamento do pedido é obrigatório")
    private FormasDePagamento formasDePagamento;

    private boolean forEntrega;
    private boolean forTakeaway;
    private boolean forDineIn;
    private boolean isPago;
}
