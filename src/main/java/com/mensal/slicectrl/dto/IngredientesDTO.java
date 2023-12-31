package com.mensal.slicectrl.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class IngredientesDTO extends AbstractEntityDTO {

    @NotNull(message = "É obrigatorio informar o nome do ingrediente")
    @NotBlank(message = "É obrigatorio informar o nome do ingrediente")
    @Size(min = 2, max = 50, message = "O nome do ingrediente deve ter entre 2 e 30 caracteres")
    private String nomeIngrediente;
    @NotNull(message = "É obrigatorio informar a quantidade de ingredientes")
    private int qtdeIngrediente;

}
