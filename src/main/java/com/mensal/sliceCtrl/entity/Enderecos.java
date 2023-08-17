package com.mensal.sliceCtrl.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="enderecos", schema = "public")
@Getter
@Setter
public class Enderecos extends AbstractEntity{

    @Column(name = "rua", nullable = false)
    private String rua;

    @Column(name = "numero", nullable = true)
    private Integer numero;

    @Column(name = "complemento", nullable = true)
    private String complemento;

    @Column(name = "bairro", nullable = false)
    private String bairro;

    @Column(name = "cidade", nullable = false)
    private String cidade;

    @Column(name = "estado", nullable = false)
    private String estado;

    @Column(name = "pais", nullable = false)
    private String pais;

    @Column(name = "cep", nullable = false)
    private String cep;

    @ManyToMany(mappedBy = "enderecos")
    @JsonIgnore
    private List<Clientes> clientes;

}
