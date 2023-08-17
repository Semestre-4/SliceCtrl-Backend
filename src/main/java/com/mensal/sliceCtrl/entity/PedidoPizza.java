package com.mensal.sliceCtrl.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "pedido_pizza",schema = "public")
@Getter
@Setter
public class PedidoPizza extends AbstractEntity{

    @ManyToOne
    @JoinColumn(name = "pizza_id",nullable = false)
    private Pizzas pizza;
    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedidos pedido;
    @Column(name = "qtde_pedida",nullable = false)
    private int qtdePedida;

}
