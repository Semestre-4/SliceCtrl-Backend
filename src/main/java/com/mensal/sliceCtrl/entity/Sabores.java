package com.mensal.sliceCtrl.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Getter
@Setter
@Table(name = "sabores", schema = "public")
public class Sabores extends AbstractEntity{

    @NotBlank(message = "É obrigatorio informar o nome do sabor")
    @NotNull(message = "É obrigatorio informar o nome do sabor")
    @Size(min = 2, max = 50, message = "O nome deve ter entre 2 e 50 caracteres")
    @Column(name = "nome_sabor", nullable = false, unique = true)
    private String nomeSabor;

    @Column(name = "descricao_sabor", nullable = true)
    private String descricao;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "sabor-ingrediente",
            joinColumns = @JoinColumn(name = "sabor_id"),
            inverseJoinColumns = @JoinColumn(name = "ingrediente_id")

    )
    private List<Ingredientes> ingredientes = new ArrayList<>();

    @ManyToMany(mappedBy = "sabor")
    @JsonIgnore
    private List<Pizzas> pizzas = new ArrayList<>();


}