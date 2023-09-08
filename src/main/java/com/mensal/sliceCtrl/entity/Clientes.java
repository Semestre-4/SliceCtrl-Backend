package com.mensal.sliceCtrl.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clientes", schema = "public")
@Getter
@Setter
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

}
