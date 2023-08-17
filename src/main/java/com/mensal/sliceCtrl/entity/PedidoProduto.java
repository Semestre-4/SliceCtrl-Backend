package com.mensal.sliceCtrl.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "pedido_produto",schema = "public")
@Getter
@Setter
public class PedidoProduto extends AbstractEntity{

    @ManyToOne
    @JoinColumn(name = "produto_id",nullable = false)
    private Produtos produto;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedidos pedido;
    private int qtdePedida;

}
