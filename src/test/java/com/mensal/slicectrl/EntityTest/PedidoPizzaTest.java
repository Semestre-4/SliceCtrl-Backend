package com.mensal.slicectrl.EntityTest;

import com.mensal.slicectrl.entity.PedidoPizza;
import com.mensal.slicectrl.entity.Pedidos;
import com.mensal.slicectrl.entity.Pizzas;
import com.mensal.slicectrl.entity.Sabores;
import com.mensal.slicectrl.entity.enums.Tamanho;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

class PedidoPizzaTest {

    private PedidoPizza pedidoPizza;

    @BeforeEach
    void setUp() {
        pedidoPizza = new PedidoPizza();
        pedidoPizza.setQtdePedida(2);
        pedidoPizza.setObservacao("Extra cheese");

        Pizzas pizza = new Pizzas();
        pizza.setTamanho(Tamanho.P);
        pedidoPizza.setPizza(pizza);

        List<Sabores> sabores = new ArrayList<>();
        Sabores sabor1 = new Sabores();
        sabor1.setNomeSabor("Tomato");
        sabores.add(sabor1);
        pedidoPizza.setSabores(sabores);

        Pedidos pedido = new Pedidos();
        pedidoPizza.setPedido(pedido);
    }

    @Test
    void testQtdePedida() {
        assertEquals(2, pedidoPizza.getQtdePedida());
    }

    @Test
    void testObservacao() {
        assertEquals("Extra cheese", pedidoPizza.getObservacao());
    }

    @Test
    void testPizza() {
        assertNotNull(pedidoPizza.getPizza());
        assertEquals(Tamanho.P, pedidoPizza.getPizza().getTamanho());
    }

    @Test
    void testSabores() {
        assertNotNull(pedidoPizza.getSabores());
        assertEquals(1, pedidoPizza.getSabores().size());
        assertEquals("Tomato", pedidoPizza.getSabores().get(0).getNomeSabor());
    }

    @Test
    void testPedido() {
        assertNotNull(pedidoPizza.getPedido());
    }
}
