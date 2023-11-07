package com.mensal.slicectrl.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
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
    @JsonIgnoreProperties("pedidos")
    private Produtos produto;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    @JsonIgnoreProperties("produtos")
    private Pedidos pedido;

    @Column(name = "qtde_pedida",nullable = false)
    private Integer qtdePedida;


}
