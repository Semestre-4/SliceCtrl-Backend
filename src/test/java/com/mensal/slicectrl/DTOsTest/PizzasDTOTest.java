package com.mensal.slicectrl.DTOsTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.mensal.slicectrl.dto.PizzasDTO;
import com.mensal.slicectrl.entity.enums.Tamanho;

class PizzasDTOTest {

    private PizzasDTO pizzasDTO;

    @BeforeEach
    void setUp() {
        pizzasDTO = new PizzasDTO();
    }

    @Test
    void testValidPizzasDTO() {
        pizzasDTO.setTamanho(Tamanho.M);
        pizzasDTO.setPreco(12.99);

        assertDoesNotThrow(() -> validatePizzasDTO(pizzasDTO));
    }

    @Test
    void testInvalidPizzasDTO() {
        assertThrows(IllegalArgumentException.class, () -> validatePizzasDTO(pizzasDTO));
    }

    private void validatePizzasDTO(PizzasDTO dto) {
        if (dto.getTamanho() == null) {
            throw new IllegalArgumentException("Tamanho cannot be null");
        }

        if (dto.getPreco() == null || dto.getPreco() <= 0) {
            throw new IllegalArgumentException("Preco must be a positive value");
        }
    }
}
