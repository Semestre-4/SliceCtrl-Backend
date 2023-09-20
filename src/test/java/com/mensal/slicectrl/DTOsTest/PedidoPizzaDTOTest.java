package com.mensal.slicectrl.DTOsTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.mensal.slicectrl.dto.PedidoPizzaDTO;

class PedidoPizzaDTOTest {

    private PedidoPizzaDTO pedidoPizzaDTO;

    @BeforeEach
    void setUp() {
        pedidoPizzaDTO = new PedidoPizzaDTO();
    }

    @Test
    void testValidPedidoPizzaDTO() {
        pedidoPizzaDTO.setQtdePedida(2);

        assertDoesNotThrow(() -> validatePedidoPizzaDTO(pedidoPizzaDTO));
    }

    @Test
    void testInvalidPedidoPizzaDTO() {
        assertThrows(IllegalArgumentException.class, () -> validatePedidoPizzaDTO(pedidoPizzaDTO));
    }

    private void validatePedidoPizzaDTO(PedidoPizzaDTO dto) {
        if (dto.getQtdePedida() < 1) {
            throw new IllegalArgumentException("Quantidade pedida must be at least 1");
        }
    }
}
