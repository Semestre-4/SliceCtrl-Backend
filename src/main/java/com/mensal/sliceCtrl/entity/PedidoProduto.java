package com.mensal.sliceCtrl.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pedido_produto",schema = "public")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoProduto extends AbstractEntity{

    @ManyToOne
    @JoinColumn(name = "produto_id",nullable = false)
    private Produtos produto;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedidos pedido;
    private Integer qtdePedida;

}
