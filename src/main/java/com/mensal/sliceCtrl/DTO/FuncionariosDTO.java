package com.mensal.sliceCtrl.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
public class FuncionariosDTO extends AbstractEntityDTO {

    @NotBlank(message = "O nome do funcionario é obrigatório")
    @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
    @NotNull(message = "O campo 'nome' não pode ser nulo")
    private String nome;

//    @CPF(message = "O CPF é inválido")
    @NotBlank(message = "O CPF do funcionario é obrigatório")
    @NotNull(message = "O campo 'CPF' não pode ser nulo")
    private String cpf;

    @NotBlank(message = "O telefone do funcionario é obrigatório")
    @NotNull(message = "O campo 'telefone' não pode ser nulo")
    private String telefone;

    @NotNull(message = "O campo 'salario' não pode ser nulo")
    private BigDecimal salario;


}
