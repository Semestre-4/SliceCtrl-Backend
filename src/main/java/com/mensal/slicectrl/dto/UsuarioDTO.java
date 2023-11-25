package com.mensal.slicectrl.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;


@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO extends AbstractEntityDTO {

    @NotBlank(message = "O nome do funcionario é obrigatório")
    @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
    @NotNull(message = "O campo 'nome' não pode ser nulo")
    private String nome;

//    @CPF(message = "O CPF é inválido")
    @NotBlank(message = "O CPF do usuario é obrigatório")
    @NotNull(message = "O campo 'CPF' não pode ser nulo")
    private String cpf;

    @NotBlank(message = "O CPF do usuario é obrigatório")
    @NotNull(message = "O campo 'Senha' não pode ser nulo")
    private String password;

    private String telefone;

    private String role;

    private String token;

}
