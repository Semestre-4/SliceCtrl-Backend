package com.mensal.sliceCtrl.DTO;

import com.mensal.sliceCtrl.entity.AbstractEntity;
import com.mensal.sliceCtrl.entity.Ingredientes;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

public class SaboresDTO extends AbstractEntityDTO {

    private String nomeSabor;

    private String descricao;

    private List<Ingredientes> ingredientes;

    public SaboresDTO() {
    }

    public SaboresDTO(String nomeSabor, List<Ingredientes> ingredientes) {
        this.nomeSabor = nomeSabor;
        this.ingredientes = ingredientes;
    }

    public SaboresDTO(String nomeSabor, String descricao, List<Ingredientes> ingredientes) {
        this.nomeSabor = nomeSabor;
        this.descricao = descricao;
        this.ingredientes = ingredientes;
    }

    public String getNomeSabor() {
        return nomeSabor;
    }


    public void setNomeSabor(String nomeSabor) {
        this.nomeSabor = nomeSabor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Ingredientes> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<Ingredientes> ingredientes) {
        this.ingredientes = ingredientes;
    }


}
