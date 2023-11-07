package com.mensal.slicectrl.entity;

import  com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="enderecos", schema = "public")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Enderecos extends AbstractEntity{

    @Column(name = "rua", nullable = true)
    private String rua;

    @Column(name = "numero", nullable = true)
    private Integer numero;

    @Column(name = "complemento", nullable = true)
    private String complemento;

    @Column(name = "bairro",nullable = true)
    private String bairro;

    @Column(name = "cidade",nullable = true)
    private String cidade;

    @Column(name = "estado",nullable = true)
    private String estado;

    @Column(name = "pais",nullable = true)
    private String pais;

    @Column(name = "cep",nullable = true)
    private String cep;

    @ManyToMany(mappedBy = "enderecos")
    @JsonIgnore
    private List<Clientes> clientes;

    public Enderecos(String rua, String complemento, String bairro, String cidade, String estado, String pais, String cep) {
        this.rua = rua;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.pais = pais;
        this.cep = cep;
    }
}
