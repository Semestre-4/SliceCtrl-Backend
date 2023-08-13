package com.mensal.sliceCtrl.entity;

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

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="enderecos", schema = "public")
@Getter
@Setter
public class Enderecos extends AbstractEntity {

    @NotNull(message = "A rua não pode ser nula")
    @Size(min = 2, max = 50, message = "A rua deve conter entre 2 e 50 caracteres")
    @Column(name = "rua", nullable = false)
    private String rua;

    @Column(name = "numero", nullable = true)
    private Integer numero;

    @Column(name = "complemento", nullable = true)
    @Size(min = 2, max = 100, message = "O complemento pode ter entre 2 e 50 caracteres")
    private String complemento;

    @NotNull(message = "É obrigatorio informar o bairro")
    @Column(name = "bairro", nullable = false)
    @Size(min = 2, max = 20, message = "O bairro pode ter entre 2 e 20 caracteres")
    private String bairro;

    @NotNull(message = "É obrigatorio informar a cidade")
    @NotBlank(message = "É obrigatorio informar a cidade")
    @Column(name = "cidade", nullable = false)
    @Size(min = 3, max = 50, message = "A cidade pode ter entre 3 e 50 caracteres")
    private String cidade;


    @NotNull(message = "É obrigatorio informar o estado")
    @NotBlank(message = "É obrigatorio informar o estado")
    @Column(name = "estado", nullable = false)
    @Size(min = 1, max = 3, message = "O estado pode ter entre 1 e 3 caracteres, informe o prefixo do estado")
    private String estado;

    @NotNull(message = "É obrigatorio informar o pais")
    @NotBlank(message = "É obrigatorio informar o pais")
    @Column(name = "pais", nullable = false)
    @Size(min = 3, max = 50, message = "O pais pode ter entre 3 e 50 caracteres")
    private String pais;

    @NotNull(message = "É obrigatorio informar o CEP")
    @Column(name = "cep", nullable = false)
    @Size(min = 7, max = 11, message = "CEP Informado incorretamente")
    private String cep;

    @ManyToMany(mappedBy = "enderecos")
    private List<Clientes> clientes = new ArrayList<>();

}
