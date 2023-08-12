package com.mensal.sliceCtrl.DTO;

import com.mensal.sliceCtrl.entity.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;


public class IngredientesDTO extends AbstractEntityDTO {

    private String nomeIngrediente;

    private double qtdeIngrediente;

    public IngredientesDTO() {
    }

    public IngredientesDTO(String nomeIngrediente, double qtdeIngrediente) {
        this.nomeIngrediente = nomeIngrediente;
        this.qtdeIngrediente = qtdeIngrediente;
    }

    public double getQtdeIngrediente() {
        return qtdeIngrediente;
    }

    public void setQtdeIngrediente(double qtdeIngrediente) {
        this.qtdeIngrediente = qtdeIngrediente;
    }

    public String getNomeIngrediente() {
        return nomeIngrediente;
    }

    public void setNomeIngrediente(String nomeIngrediente) {
        this.nomeIngrediente = nomeIngrediente;
    }
}
