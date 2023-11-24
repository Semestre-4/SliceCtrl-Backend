package com.mensal.slicectrl.dto;

import com.mensal.slicectrl.entity.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Getter @Setter
@NoArgsConstructor
public class UsuarioDTO extends AbstractEntityDTO {

    @NotBlank(message = "O nome do funcionario é obrigatório")
    @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
    @NotNull(message = "O campo 'nome' não pode ser nulo")
    private String nome;

    @NotBlank(message = "O username do funcionario é obrigatório")
    @Size(min = 2, max = 100, message = "O username deve ter entre 2 e 100 caracteres")
    @NotNull(message = "O campo 'username' não pode ser nulo")
    private String username;

//    @CPF(message = "O CPF é inválido")
    @NotBlank(message = "O CPF do usuario é obrigatório")
    @NotNull(message = "O campo 'CPF' não pode ser nulo")
    private String cpf;

    @NotBlank(message = "O CPF do usuario é obrigatório")
    @NotNull(message = "O campo 'Senha' não pode ser nulo")
    private String password;

    private Set<Role> roles;

    @NotBlank(message = "O telefone do usuario é obrigatório")
    @NotNull(message = "O campo 'telefone' não pode ser nulo")
    private String telefone;

    private BigDecimal salario;

    private String token;



    public UsuarioDTO(String nome, String cpf, String telefone, BigDecimal salario) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.salario = salario;
    }
}
