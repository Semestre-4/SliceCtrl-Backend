package com.mensal.sliceCtrl.entity;

import com.mensal.sliceCtrl.entity.enums.Tamanho;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "pizzas",schema = "public")
@Getter
@Setter
public class Pizza extends Produtos {

    @NotBlank(message = "O tamanho da pizza é obrigatório")
    @Column(name = "tamanho_pizza",nullable = true)
    private Tamanho tamanho;

    @Column(name = "observacao")
    private String observacao;

    @ManyToMany(mappedBy = "pizzas")
    private List<Pedido> pedidos = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "pizza_sabor",
            joinColumns = @JoinColumn(name = "pizza_id"),
            inverseJoinColumns = @JoinColumn(name = "sabor_id")
    )
    private List<Sabores> sabor;

}
