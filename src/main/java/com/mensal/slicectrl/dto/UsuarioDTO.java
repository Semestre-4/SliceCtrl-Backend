package com.mensal.slicectrl.dto;

import com.mensal.slicectrl.entity.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import java.util.List;

@Getter @Setter
public class UsuarioDTO extends AbstractEntityDTO {

    @NotBlank(message = "O nome do funcionario é obrigatório")
    @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
    @NotNull(message = "O campo 'nome' não pode ser nulo")
    private String nome;

    @CPF(message = "O CPF é inválido")
    @NotBlank(message = "O CPF do usuario é obrigatório")
    @NotNull(message = "O campo 'CPF' não pode ser nulo")
    private String cpf;

    @NotBlank(message = "O CPF do usuario é obrigatório")
    @NotNull(message = "O campo 'Senha' não pode ser nulo")
    private String password;

    private List<Role> roles;

    private String token;

}
