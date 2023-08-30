package com.mensal.sliceCtrl.DTO;


import com.mensal.sliceCtrl.entity.Pedidos;
import com.mensal.sliceCtrl.entity.Sabores;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoPizzaDTO extends AbstractEntityDTO {

    @NotNull(message = "A pizza é obrigatória")
    private PizzasDTO pizza;
    private Pedidos pedido;
    @Min(value = 1, message = "A quantidade pedida deve ser pelo menos 1")
    private int qtdePedida;
    @NotNull(message = "O sabor é obrigatório")
    private Sabores sabor;
    private String observacao;

}
