package com.mensal.slicectrl.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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
    private List<SaboresDTO> sabores = new ArrayList<>();
    @Min(value = 1, message = "A quantidade pedida deve ser pelo menos 1")
    private int qtdePedida;
    private String observacao;
    @NotNull(message = "O valor não pode ser nulo")
    private Double valor;
}
