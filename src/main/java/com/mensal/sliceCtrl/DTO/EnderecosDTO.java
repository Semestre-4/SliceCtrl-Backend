package com.mensal.sliceCtrl.DTO;

import com.mensal.sliceCtrl.entity.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
public class EnderecosDTO extends AbstractEntityDTO {

    @NotNull(message = "A rua não pode ser nula")
    @Size(min = 2, max = 50, message = "A rua deve conter entre 2 e 50 caracteres")
    private String rua;
    private Integer numero;

    @Size(min = 2, max = 100, message = "O complemento pode ter entre 2 e 50 caracteres")
    private String complemento;

    @NotNull(message = "É obrigatorio informar o bairro")
    @Size(min = 2, max = 20, message = "O bairro pode ter entre 2 e 20 caracteres")
    private String bairro;

    @NotNull(message = "É obrigatorio informar a cidade")
    @NotBlank(message = "É obrigatorio informar a cidade")
    @Size(min = 3, max = 50, message = "A cidade pode ter entre 3 e 50 caracteres")
    private String cidade;
    @NotNull(message = "É obrigatorio informar o estado")
    @NotBlank(message = "É obrigatorio informar o estado")

    @Size(min = 1, max = 3, message = "O estado pode ter entre 1 e 3 caracteres, informe o prefixo do estado")
    private String estado;
    @NotNull(message = "É obrigatorio informar o pais")
    @NotBlank(message = "É obrigatorio informar o pais")
    @Size(min = 3, max = 50, message = "O pais pode ter entre 3 e 50 caracteres")
    private String pais;
    @NotNull(message = "É obrigatorio informar o CEP")
    @Size(min = 7, max = 11, message = "CEP Informado incorretamente")
    private String cep;


}
