package com.mensal.sliceCtrl.DTO;


import com.mensal.sliceCtrl.entity.Ingredientes;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class SaboresDTO extends AbstractEntityDTO {

    @NotBlank(message = "É obrigatorio informar o nome do sabor")
    @NotNull(message = "É obrigatorio informar o nome do sabor")
    @Size(min = 2, max = 50, message = "O nome deve ter entre 2 e 50 caracteres")
    private String nomeSabor;
    private String descricao;
    private double valorAdicional;
    @NotNull(message = "É obrigatorio informar os ingredientes")
    private List<IngredientesDTO> ingredientes;

}
