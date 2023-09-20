package com.mensal.slicectrl.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name="ingredientes", schema = "public")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ingredientes extends AbstractEntity{

    @Column(name = "nome_ingrediente", nullable = false,unique = true)
    private String nomeIngrediente;

    @Column(name = "quantidade_ingrediente", nullable = false)
    private double qtdeIngrediente;

    @ManyToMany(mappedBy = "ingredientes")
    @JsonIgnore
    private List<Sabores> sabores = new ArrayList<>();

    public Ingredientes(String nomeIngrediente, double qtdeIngrediente) {
        this.nomeIngrediente = nomeIngrediente;
        this.qtdeIngrediente = qtdeIngrediente;
    }
}
