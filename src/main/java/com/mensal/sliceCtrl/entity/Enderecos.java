package com.mensal.sliceCtrl.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="enderecos", schema = "public")
@Data
public class Enderecos extends AbstractEntity {

    @Column(name = "numero", nullable = true)
    Integer numero;

    @Column(name = "complemento", nullable = true)
    @Size(min = 2, max = 100, message = "O complemento pode ter entre 2 e 50 caracteres")
    String complemento;

    @NotNull(message = "É obrigatorio informar o bairro")
    @Column(name = "bairro", nullable = false)
    @Size(min = 2, max = 20, message = "O bairro pode ter entre 2 e 20 caracteres")
    String Bairro;

    @NotNull(message = "É obrigatorio informar o CEP")
    @Column(name = "cep", nullable = false)
    @Size(min = 7, max = 11, message = "CEP Informado incorretamente")
    String CEP;

}
