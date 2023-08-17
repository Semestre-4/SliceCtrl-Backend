package com.mensal.sliceCtrl.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @NotNull(message = "É obrigatorio informar o tamanho da pizza")
    @Enumerated(EnumType.STRING)
    private Tamanho tamanho;

    @Column(name = "observacao")
    private String observacao;

    @NotNull(message = "É obrigatorio informar o preço do produto")
    @Column(name = "preco_produto", nullable = false)
    private Double preco;

    @NotNull(message = "É obrigatorio informar a quantidade de estoque")
    @Column(name = "qtde_estoque", nullable = false)
    private Integer qtdeEstoque;

    @OneToMany(mappedBy = "pizza")
    private List<PedidoPizza> pedidos = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "pizza_sabor",
            joinColumns = @JoinColumn(name = "pizza_id"),
            inverseJoinColumns = @JoinColumn(name = "sabor_id")
    )
    @JsonIgnore
    @NotNull(message = "É obrigatorio informar pelo menos 1 sabor")
    private List<Sabores> sabor;

    @Column(name = "is_disponivel", nullable = false)
    private boolean disponivel;

    @PrePersist @PreUpdate
    public void UpdateDisponivelFlag(){
        if (qtdeEstoque != null && qtdeEstoque > 0){
            setDisponivel(true);
        }
    }



}
