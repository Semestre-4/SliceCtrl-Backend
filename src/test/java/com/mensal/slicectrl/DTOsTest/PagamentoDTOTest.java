package com.mensal.slicectrl.DTOsTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.mensal.slicectrl.dto.PagamentoDTO;
import com.mensal.slicectrl.entity.enums.FormasDePagamento;

class PagamentoDTOTest {

    private PagamentoDTO pagamentoDTO;

    @BeforeEach
    void setUp() {
        pagamentoDTO = new PagamentoDTO();
    }

    @Test
    void testValidPagamentoDTO() {
        pagamentoDTO.setFormasDePagamento(FormasDePagamento.CREDITO);
        pagamentoDTO.setPago(true);

        assertDoesNotThrow(() -> validatePagamentoDTO(pagamentoDTO));
    }

    @Test
    void testInvalidPagamentoDTO() {
        assertThrows(IllegalArgumentException.class, () -> validatePagamentoDTO(pagamentoDTO));
    }

    private void validatePagamentoDTO(PagamentoDTO dto) {
        if (dto.getFormasDePagamento() == null) {
            throw new IllegalArgumentException("A forma de pagamento não pode ser nula");
        }
    }
}
