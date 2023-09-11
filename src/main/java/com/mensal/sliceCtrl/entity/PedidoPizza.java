package com.mensal.sliceCtrl.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "pedido_pizza",schema = "public")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoPizza extends AbstractEntity{

    @ManyToOne
    @JoinColumn(name = "pizza_id",nullable = false)
    @JsonIgnoreProperties("pedidos")
    private Pizzas pizza;

    @ManyToMany
    @JoinTable(
            name = "pedido_pizza_sabor",
            joinColumns = @JoinColumn(name = "pedido_pizza_id"),
            inverseJoinColumns = @JoinColumn(name = "sabor_id")
    )
//    @JsonIgnoreProperties("pedidosPizza")
    private List<Sabores> sabores = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    @JsonIgnoreProperties("pizzas")
    private Pedidos pedido;

    @Column(name = "qtde_pedida",nullable = false)
    private Integer qtdePedida;

    @Column(name = "observacao")
    private String observacao;


}
