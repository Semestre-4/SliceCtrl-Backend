package com.mensal.sliceCtrl.DTO;

import com.mensal.sliceCtrl.entity.Enderecos;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import java.util.List;

@Getter @Setter
public class ClientesDTO extends AbstractEntityDTO{

    @NotBlank(message = "O nome do cliente é obrigatório")
    @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
    @NotNull(message = "O campo 'nome' não pode ser nulo")
    private String nome;

//    @CPF(message = "O CPF é inválido")
    @NotBlank(message = "O CPF do cliente é obrigatório")
    @NotNull(message = "O campo 'CPF' não pode ser nulo")
    private String cpf;

    @NotBlank(message = "O telefone do cliente é obrigatório")
    @NotNull(message = "O campo 'telefone' não pode ser nulo")
    private String telefone;

    @Email(message = "O email é inválido")
    private String email;

    @NotNull(message = "O cliente deve ter pelo menos um endereço vinculado.")
    private List<EnderecosDTO> enderecos;

}
