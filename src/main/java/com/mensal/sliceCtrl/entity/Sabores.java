package com.mensal.sliceCtrl.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;


import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@Table(name = "sabores", schema = "public")
public class Sabores extends AbstractEntity {

    @Column(name = "nome_sabor", nullable = false, unique = true)
    private String nomeSabor;

    @Column(name = "descricao_sabor", nullable = true)
    private String descricao;

    @Column(name = "valor_adicional", nullable = true)
    private double valorAdicional;

    @ManyToMany
    @JoinTable(
            name = "sabor-ingrediente",
            joinColumns = @JoinColumn(name = "sabor_id"),
            inverseJoinColumns = @JoinColumn(name = "ingrediente_id")

    )
    private List<Ingredientes> ingredientes = new ArrayList<>();

    @OneToMany(mappedBy = "sabor", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("sabor")
    private List<PedidoPizza> pedidosPizza;

}