package com.mensal.sliceCtrl.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mensal.sliceCtrl.entity.enums.Categoria;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "produtos", schema = "public")
@Getter
@Setter
public class Produtos extends AbstractEntity {

    @Column(name = "nome_produto", nullable = false, unique = true)
    String nomeProduto;

    @Column(name = "categoria", nullable = false)
    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    @Column(name = "qtde_estoque", nullable = false)
    private Integer qtdeEstoque;

    @Column(name = "preco_produto", nullable = false)
    private Double preco;

    @Column(name = "is_disponivel", nullable = false)
    private boolean disponivel;

    @OneToMany(mappedBy = "produto",cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JsonIgnoreProperties("produto")
    private List<PedidoProduto> pedidos = new ArrayList<>();

}
