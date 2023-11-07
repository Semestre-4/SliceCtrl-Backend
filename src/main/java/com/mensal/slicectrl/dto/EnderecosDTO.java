package com.mensal.slicectrl.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnderecosDTO extends AbstractEntityDTO {

    @Size(min = 2, max = 50, message = "A rua deve conter entre 2 e 50 caracteres")
    private String rua;
    private Integer numero;

    @Size(min = 2, max = 100, message = "O complemento pode ter entre 2 e 50 caracteres")
    private String complemento;

    @Size(min = 2, max = 20, message = "O bairro pode ter entre 2 e 20 caracteres")
    private String bairro;

    @Size(min = 3, max = 50, message = "A cidade pode ter entre 3 e 50 caracteres")
    private String cidade;

    @Size(min = 1, max = 3, message = "O estado pode ter entre 1 e 3 caracteres, informe o prefixo do estado")
    private String estado;

    @Size(min = 3, max = 50, message = "O pais pode ter entre 3 e 50 caracteres")
    private String pais;
    @Size(min = 7, max = 11, message = "CEP Informado incorretamente")
    private String cep;



}
