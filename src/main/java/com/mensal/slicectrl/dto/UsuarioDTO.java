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

//    @CPF(message = "O CPF é inválido")
    @NotBlank(message = "O CPF do usuario é obrigatório")
    @NotNull(message = "O campo 'CPF' não pode ser nulo")
    private String cpf;

    private String role;

    private String token;

}
