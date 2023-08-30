package com.mensal.sliceCtrl.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mensal.sliceCtrl.entity.enums.Tamanho;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "pizzas",schema = "public")
@Getter
@Setter
public class Pizzas extends AbstractEntity{

    @Column(name = "tamanho_pizza",nullable = false)
    @Enumerated(EnumType.STRING)
    private Tamanho tamanho;

    @Column(name = "preco_produto", nullable = false)
    private Double preco;

    @Column(name = "discricao")
    private String discricao;

    @OneToMany(mappedBy = "pizza",cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JsonIgnoreProperties("pizza")
    private List<PedidoPizza> pedidos = new ArrayList<>();

    @Column(name = "is_disponivel", nullable = false)
    private boolean disponivel;

}
