package com.mensal.slicectrl.EntityTest;

import com.mensal.slicectrl.entity.PedidoPizza;
import com.mensal.slicectrl.entity.Pizzas;
import com.mensal.slicectrl.entity.enums.Tamanho;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

class PizzasTest {

    private Pizzas pizzas;

    @BeforeEach
    void setUp() {
        pizzas = new Pizzas(Tamanho.M, 12.99);
        pizzas.setDiscricao("Delicious pizza");
        pizzas.setDisponivel(true);

        List<PedidoPizza> pedidos = new ArrayList<>();
        PedidoPizza pedido1 = new PedidoPizza();
        pedido1.setQtdePedida(2);
        pedido1.setPizza(pizzas);
        pedidos.add(pedido1);
        pizzas.setPedidos(pedidos);
    }

    @Test
    void testTamanho() {
        assertEquals(Tamanho.M, pizzas.getTamanho());
    }

    @Test
    void testPreco() {
        assertEquals(12.99, pizzas.getPreco(), 0.01); // Using delta for double comparison
    }

    @Test
    void testDiscricao() {
        assertEquals("Delicious pizza", pizzas.getDiscricao());
    }

    @Test
    void testDisponivel() {
        assertTrue(pizzas.isDisponivel());
    }

    @Test
     void testPedidos() {
        assertNotNull(pizzas.getPedidos());
        assertEquals(1, pizzas.getPedidos().size());
        assertEquals(2, pizzas.getPedidos().get(0).getQtdePedida());
    }
}
