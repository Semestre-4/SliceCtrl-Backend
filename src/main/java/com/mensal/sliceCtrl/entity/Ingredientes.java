package com.mensal.sliceCtrl.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name="ingredientes", schema = "public")
@Data
public class Ingredientes extends AbstractEntity{

    @NotNull(message = "É obrigatorio informar o nome do ingrediente")
    @NotBlank(message = "É obrigatorio informar o nome do ingrediente")
    @Size(min = 2, max = 50, message = "O nome do ingrediente deve ter entre 2 e 30 caracteres")
    @Column(name = "nome_ingrediente", nullable = false)
    private String nomeIngrediente;

    @NotNull(message = "É obrigatorio informar a quantidade de ingredientes")
    @Column(name = "quantidade_ingrediente", nullable = false)
    private double qtdeIngrediente;

}
