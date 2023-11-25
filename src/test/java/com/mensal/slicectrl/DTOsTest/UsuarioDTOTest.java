package com.mensal.slicectrl.DTOsTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.mensal.slicectrl.dto.UsuarioDTO;

import java.math.BigDecimal;

class UsuarioDTOTest {

    private UsuarioDTO usuarioDTO;

    @BeforeEach
    void setUp() {
        usuarioDTO = new UsuarioDTO();
    }

    @Test
    void testValidFuncionariosDTO() {
        usuarioDTO.setNome("John Doe");
        usuarioDTO.setCpf("12345678901");
        usuarioDTO.setTelefone("123-456-7890");
        usuarioDTO.setSalario(new BigDecimal("2500.00"));

        assertDoesNotThrow(() -> validateFuncionariosDTO(usuarioDTO));
    }

    @Test
    void testInvalidFuncionariosDTO() {
        assertThrows(IllegalArgumentException.class, () -> validateFuncionariosDTO(usuarioDTO));
    }

    void validateFuncionariosDTO(UsuarioDTO dto) {
        if (dto.getNome() == null || dto.getNome().length() < 2 || dto.getNome().length() > 100) {
            throw new IllegalArgumentException("Invalid nome");
        }

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
