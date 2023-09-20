package com.mensal.slicectrl.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mensal.slicectrl.entity.enums.Tamanho;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "pizzas",schema = "public")
@Getter
@Setter
@NoArgsConstructor
public class Pizzas extends AbstractEntity{

    @Column(name = "tamanho_pizza",nullable = false)
    @Enumerated(EnumType.STRING)
    private Tamanho tamanho;

    @Column(name = "preco_pizza", nullable = false)
    private Double preco;

    @Column(name = "discricao")
    private String discricao;

    @OneToMany(mappedBy = "pizza",cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JsonIgnoreProperties("pizza")
    private List<PedidoPizza> pedidos = new ArrayList<>();

    @Column(name = "is_disponivel", nullable = false)
    private boolean disponivel;

    public Pizzas(Tamanho tamanho, Double preco) {
        this.tamanho = tamanho;
        this.preco = preco;
    }
}
