package com.mensal.sliceCtrl.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoPizzaDTO extends AbstractEntityDTO {

    @NotNull(message = "A pizza não pode ser nula")
    @JsonIgnoreProperties("pedidos")
    private PizzasDTO pizza;

    @JsonIgnoreProperties("pizzas")
    private PedidosDTO pedido;
    @NotNull(message = "O sabor não pode ser nulo")
    @JsonIgnoreProperties("pedidosPizza")
    private SaboresDTO sabor;
    @Min(value = 1, message = "A quantidade pedida deve ser pelo menos 1")
    private int qtdePedida;
    private String observacao;

}
