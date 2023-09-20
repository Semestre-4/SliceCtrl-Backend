package com.mensal.slicectrl.EntityTest;

import com.mensal.slicectrl.entity.Pagamento;
import com.mensal.slicectrl.entity.Pedidos;
import com.mensal.slicectrl.entity.enums.FormasDePagamento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PagamentoTest {

    private Pagamento pagamento;

    @BeforeEach
    void setUp() {
        pagamento = new Pagamento();
        pagamento.setFormasDePagamento(FormasDePagamento.CREDITO);
        pagamento.setPago(true);

        Pedidos pedido = new Pedidos();
        pedido.setPagamento(pagamento);
        pagamento.setPedido(pedido);
    }

    @Test
    void testFormasDePagamento() {
        assertEquals(FormasDePagamento.CREDITO, pagamento.getFormasDePagamento());
    }

    @Test
    void testIsPago() {
        assertTrue(pagamento.isPago());
    }

    @Test
    void testPedido() {
        assertNotNull(pagamento.getPedido());
    }
}
