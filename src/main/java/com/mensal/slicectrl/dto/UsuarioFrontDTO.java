package com.mensal.slicectrl.dto;

import com.mensal.slicectrl.entity.Pedidos;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioFrontDTO extends AbstractEntityDTO{

    private String nome;

    private String cpf;

    private String role;

    private String password;

    private String telefone;

    private BigDecimal salario;

    private List<Pedidos> pedidos;

    public UsuarioFrontDTO(String cpf, String role, String telefone) {
    }
}
