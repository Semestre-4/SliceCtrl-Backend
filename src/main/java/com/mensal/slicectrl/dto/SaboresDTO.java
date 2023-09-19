package com.mensal.slicectrl.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class SaboresDTO extends AbstractEntityDTO {

    @NotBlank(message = "O nome do sabor não pode estar em branco")
    private String nomeSabor;

    private String descricao;

    @PositiveOrZero(message = "O valor adicional do sabor deve ser um valor positivo ou zero")
    private double valorAdicional;

    @NotNull(message = "A lista de ingredientes não pode ser nula")
    private List<IngredientesDTO> ingredientesDTOS;

    private List<PedidoPizzaDTO> pedidosPizza;

    public SaboresDTO(String nomeSabor, List<IngredientesDTO> ingredientesDTOList) {
        this.nomeSabor = nomeSabor;
        this.ingredientesDTOS = ingredientesDTOList;
    }
}
