package com.mensal.slicectrl.DTOsTest;

import com.mensal.slicectrl.dto.UsuarioFrontDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.mensal.slicectrl.dto.UsuarioDTO;

import java.math.BigDecimal;

class UsuarioDTOTest {

    private UsuarioFrontDTO usuarioDTO;

    @BeforeEach
    void setUp() {
        usuarioDTO = new UsuarioFrontDTO();
    }

    @Test
    void testValidFuncionariosDTO() {
        usuarioDTO.setCpf("12345678901");
        usuarioDTO.setTelefone("123-456-7890");
        usuarioDTO.setSalario(new BigDecimal("2500.00"));

        assertDoesNotThrow(() -> validateFuncionariosDTO(usuarioDTO));
    }

    @Test
    void testInvalidFuncionariosDTO() {
        assertThrows(IllegalArgumentException.class, () -> validateFuncionariosDTO(usuarioDTO));
    }

    void validateFuncionariosDTO(UsuarioFrontDTO dto) {

        if (dto.getCpf() == null || dto.getCpf().isEmpty()) {
            throw new IllegalArgumentException("Invalid cpf");
        }

        if (dto.getTelefone() == null || dto.getTelefone().isEmpty()) {
            throw new IllegalArgumentException("Invalid telefone");
        }

        if (dto.getSalario() == null || dto.getSalario().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Invalid salario");
        }
    }
}
