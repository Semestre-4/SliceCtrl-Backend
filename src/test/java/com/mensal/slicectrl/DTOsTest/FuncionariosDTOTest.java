package com.mensal.slicectrl.DTOsTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.mensal.slicectrl.dto.FuncionariosDTO;

import java.math.BigDecimal;

class FuncionariosDTOTest {

    private FuncionariosDTO funcionariosDTO;

    @BeforeEach
    void setUp() {
        funcionariosDTO = new FuncionariosDTO();
    }

    @Test
    void testValidFuncionariosDTO() {
        funcionariosDTO.setNome("John Doe");
        funcionariosDTO.setCpf("12345678901");
        funcionariosDTO.setTelefone("123-456-7890");
        funcionariosDTO.setSalario(new BigDecimal("2500.00"));

        assertDoesNotThrow(() -> validateFuncionariosDTO(funcionariosDTO));
    }

    @Test
    void testInvalidFuncionariosDTO() {
        assertThrows(IllegalArgumentException.class, () -> validateFuncionariosDTO(funcionariosDTO));
    }

    void validateFuncionariosDTO(FuncionariosDTO dto) {
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
