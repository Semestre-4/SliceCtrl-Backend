package com.mensal.sliceCtrl.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "pedido_pizza",schema = "public")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoPizza extends AbstractEntity{

    @ManyToOne
    @JoinColumn(name = "pizza_id",nullable = false)
    private Pizzas pizza;
    @ManyToOne
    @JoinColumn(name = "pedido_id")
    @JsonIgnoreProperties("pizzas")
    private Pedidos pedido;

    @Column(name = "qtde_pedida",nullable = false)
    private Integer qtdePedida;


}
