package com.mensal.sliceCtrl.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import java.util.List;

@Entity
@Table(name = "clientes", schema = "public")
@Data
public class Cliente extends AbstractEntity {

    @NotBlank(message = "O nome do cliente é obrigatório")
    @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
    @NotNull(message = "O campo 'nome' não pode ser nulo")
    @Column(name = "nome-cliente", nullable = false,length = 100)
    private String nome;

    @CPF(message = "O CPF é inválido")
    @NotBlank(message = "O CPF do cliente é obrigatório")
    @NotNull(message = "O campo 'CPF' não pode ser nulo")
    @Column(name = "cpf-cliente", nullable = false, unique = true)
    private String cpf;

    @NotBlank(message = "O telefone do cliente é obrigatório")
    @NotNull(message = "O campo 'telefone' não pode ser nulo")
    @Column(name = "telefone-cliente", nullable = false)
    private String telefone;

    @Email(message = "O email é inválido")
    @Column(name = "email-cliente")
    private String email;

//    @Size(min = 1, message = "A lista de pedidos deve conter pelo menos um item")
//    @OneToMany(mappedBy = "cliente")
//    private List<Pedido> pedidos;

}
