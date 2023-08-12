package com.mensal.sliceCtrl.entity;

import com.mensal.sliceCtrl.entity.enums.Tamanho;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Entity
@Table(name = "pizzas",schema = "public")
@Data
public class Pizza {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O tamanho da pizza é obrigatório")
    @Column(name = "tamanho_pizza",nullable = false)
    private Tamanho tamanho;

    @Column(name = "observacao")
    private String observacao;

//    @ManyToMany
//    @JoinTable(
//            name = "pizza_sabor",
//            joinColumns = @JoinColumn(name = "pizza_id"),
//            inverseJoinColumns = @JoinColumn(name = "sabor_id")
//    )
//    private List<Sabor> sabor;

}
