package com.mensal.slicectrl.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "funcionarios",schema = "public")
@Getter
@Setter
public class Usuario extends AbstractEntity{

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "cpf", nullable = false, unique = true)
    private String cpf;

    @Column(name = "password", nullable = false,unique = true)
    private String password;

    @Column(name = "telefone", nullable = false, unique = true)
    private String telefone;

    @Column(name = "salario")
    private BigDecimal salario;

    @OneToMany(mappedBy = "funcionario",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Pedidos> pedidos;

}