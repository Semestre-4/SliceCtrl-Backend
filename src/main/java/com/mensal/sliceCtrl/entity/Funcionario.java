package com.mensal.sliceCtrl.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import java.math.BigDecimal;

@Entity
@Table(name = "funcionarios",schema = "public")
@Data
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do funcionario é obrigatório")
    @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
    @NotNull(message = "O campo 'nome' não pode ser nulo")
    @Column(name = "nome-func", nullable = false)
    private String nome;

    @CPF(message = "O CPF é inválido")
    @NotBlank(message = "O CPF do funcionario é obrigatório")
    @NotNull(message = "O campo 'CPF' não pode ser nulo")
    @Column(name = "cpf-func", nullable = false, unique = true)
    private String cpf;

    @NotBlank(message = "O telefone do cliente é obrigatório")
    @NotNull(message = "O campo 'telefone' não pode ser nulo")
    @Column(name = "telefone-cliente", nullable = false)
    private String telefone;

    @Column(name = "salario_func",nullable = false)
    @NotNull(message = "O campo 'salario' não pode ser nulo")
    private BigDecimal salario;

}
