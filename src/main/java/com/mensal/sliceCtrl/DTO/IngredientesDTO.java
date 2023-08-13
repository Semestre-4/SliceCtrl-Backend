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
    private String nomeIngrediente;
    private double qtdeIngrediente;

}
