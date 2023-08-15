package com.mensal.sliceCtrl.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name="ingredientes", schema = "public")
@Getter
@Setter
public class Ingredientes extends AbstractEntity{

    @NotNull(message = "É obrigatorio informar o nome do ingrediente")
    @NotBlank(message = "É obrigatorio informar o nome do ingrediente")
    @Size(min = 2, max = 50, message = "O nome do ingrediente deve ter entre 2 e 30 caracteres")
    @Column(name = "nome_ingrediente", nullable = false)
    private String nomeIngrediente;

    @NotNull(message = "É obrigatorio informar a quantidade de ingredientes")
    @Column(name = "quantidade_ingrediente", nullable = false)
    private double qtdeIngrediente;

    @ManyToMany(mappedBy = "ingredientes")
    @JsonIgnore
    private List<Sabores> sabores = new ArrayList<>();



}
