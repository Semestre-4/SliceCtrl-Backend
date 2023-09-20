package com.mensal.slicectrl.DTOsTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.mensal.slicectrl.dto.PedidoProdutoDTO;

class PedidoProdutoDTOTest {

    private PedidoProdutoDTO pedidoProdutoDTO;

    @BeforeEach
    void setUp() {
        pedidoProdutoDTO = new PedidoProdutoDTO();
    }

    @Test
    void testValidPedidoProdutoDTO() {
        pedidoProdutoDTO.setQtdePedida(2);

        assertDoesNotThrow(() -> validatePedidoProdutoDTO(pedidoProdutoDTO));
    }

    @Test
    void testInvalidPedidoProdutoDTO() {
        assertThrows(IllegalArgumentException.class, () -> validatePedidoProdutoDTO(pedidoProdutoDTO));
    }

    private void validatePedidoProdutoDTO(PedidoProdutoDTO dto) {
        if (dto.getQtdePedida() < 1) {
            throw new IllegalArgumentException("Quantidade pedida must be at least 1");
        }
    }
}
