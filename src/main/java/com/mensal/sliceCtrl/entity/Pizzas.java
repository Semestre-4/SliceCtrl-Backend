package com.mensal.sliceCtrl.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mensal.sliceCtrl.entity.enums.Tamanho;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "pizzas",schema = "public")
@Getter
@Setter
public class Pizzas extends AbstractEntity {

    @Column(name = "tamanho_pizza",nullable = true)
    @Enumerated(EnumType.STRING)
    private Tamanho tamanho;

    @Column(name = "observacao")
    private String observacao;

    @NotNull(message = "É obrigatorio informar o preço do produto")
    @Column(name = "preco_produto", nullable = false)
    private Double preco;

    @ManyToMany(mappedBy = "pizzas")
    private List<Pedidos> pedidos = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "pizza_sabor",
            joinColumns = @JoinColumn(name = "pizza_id"),
            inverseJoinColumns = @JoinColumn(name = "sabor_id")
    )
    private List<Sabores> sabor;

    @Column(name = "is_disponivel", nullable = false)
    private boolean disponivel;


}
