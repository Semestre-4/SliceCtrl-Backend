package com.mensal.sliceCtrl.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name="ingredientes", schema = "public")
@Getter
@Setter
public class Ingredientes extends AbstractEntity{

    @Column(name = "nome_ingrediente", nullable = false,unique = true)
    private String nomeIngrediente;

    @Column(name = "quantidade_ingrediente", nullable = false)
    private double qtdeIngrediente;

    @ManyToMany(mappedBy = "ingredientes")
    @JsonIgnore
    private List<Sabores> sabores = new ArrayList<>();



}
