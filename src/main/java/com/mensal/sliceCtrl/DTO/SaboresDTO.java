package com.mensal.sliceCtrl.DTO;


import com.mensal.sliceCtrl.entity.Ingredientes;
import com.mensal.sliceCtrl.entity.PedidoPizza;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter @Setter
public class SaboresDTO extends AbstractEntityDTO {

    @NotBlank(message = "O nome do sabor não pode estar em branco")
    private String nomeSabor;

    private String descricao;

    @PositiveOrZero(message = "O valor adicional do sabor deve ser um valor positivo ou zero")
    private double valorAdicional;

    @NotNull(message = "A lista de ingredientes não pode ser nula")
    private List<IngredientesDTO> ingredientesDTOS;

    private List<PedidoPizzaDTO> pedidosPizza;

}
