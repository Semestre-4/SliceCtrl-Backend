package com.mensal.slicectrl.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clientes", schema = "public")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Clientes extends AbstractEntity {

    @Column(name = "nome-cliente", nullable = false,length = 100)
    private String nome;

    @Column(name = "cpf-cliente", nullable = false, unique = true)
    private String cpf;

    @Column(name = "telefone-cliente", nullable = false)
    private String telefone;

    @Column(name = "email-cliente")
    private String email;

    @ManyToMany(cascade = {CascadeType.ALL, CascadeType.PERSIST})
    @JoinTable(name = "cliente_endereco",
            joinColumns = @JoinColumn(name = "cliente_id"),
            inverseJoinColumns = @JoinColumn(name = "endereco_id"))
    private List<Enderecos> enderecos = new ArrayList<>();

    @OneToMany(mappedBy = "cliente")
    @JsonIgnore
    private List<Pedidos> pedidos;


    public void removeEndereco(Enderecos endereco) {
        enderecos.remove(endereco);
        endereco.getClientes().remove(this);
    }

    public Clientes(String nome, String cpf, String telefone, String email, List<Enderecos> enderecos) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
        this.enderecos = enderecos;
    }
}
