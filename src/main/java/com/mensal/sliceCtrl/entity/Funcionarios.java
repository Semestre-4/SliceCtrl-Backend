package com.mensal.sliceCtrl.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "funcionarios",schema = "public")
@Getter
@Setter
public class Funcionarios extends AbstractEntity{

    @Column(name = "nome-func", nullable = false)
    private String nome;

    @Column(name = "cpf-func", nullable = false, unique = true)
    private String cpf;

    @Column(name = "telefone-cliente", nullable = false)
    private String telefone;

    @Column(name = "salario_func",nullable = false)
    private BigDecimal salario;

    @OneToMany(mappedBy = "funcionario",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Pedidos> pedidos;

}