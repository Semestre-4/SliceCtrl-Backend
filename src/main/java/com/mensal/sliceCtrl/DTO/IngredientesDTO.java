package com.mensal.sliceCtrl.DTO;

import com.mensal.sliceCtrl.entity.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class IngredientesDTO extends AbstractEntityDTO {

    @NotNull(message = "É obrigatorio informar o nome do ingrediente")
    @NotBlank(message = "É obrigatorio informar o nome do ingrediente")
    @Size(min = 2, max = 50, message = "O nome do ingrediente deve ter entre 2 e 30 caracteres")
    private String nomeIngrediente;

    @NotNull(message = "É obrigatorio informar a quantidade de ingredientes")
    private double qtdeIngrediente;

}
