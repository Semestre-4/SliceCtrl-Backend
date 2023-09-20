package com.mensal.slicectrl.EntityTest;

import com.mensal.slicectrl.entity.Ingredientes;
import com.mensal.slicectrl.entity.PedidoPizza;
import com.mensal.slicectrl.entity.Sabores;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class SaboresTest {

    private Sabores sabores;

    @BeforeEach
    public void setUp() {
        sabores = new Sabores();
        sabores.setNomeSabor("Margarita");
        sabores.setDescricao("Classic margarita pizza");
        sabores.setValorAdicional(2.0);

        List<Ingredientes> ingredientes = new ArrayList<>();
        Ingredientes ingrediente1 = new Ingredientes();
        ingrediente1.setNomeIngrediente("Tomato Sauce");
        ingredientes.add(ingrediente1);
        sabores.setIngredientes(ingredientes);

        List<PedidoPizza> pedidosPizza = new ArrayList<>();
        PedidoPizza pedido1 = new PedidoPizza();
        pedido1.setQtdePedida(2);
        pedido1.getSabores().add(sabores);
        pedidosPizza.add(pedido1);
        sabores.setPedidosPizza(pedidosPizza);
    }

    @Test
    public void testNomeSabor() {
        assertEquals("Margarita", sabores.getNomeSabor());
    }

    @Test
    public void testDescricao() {
        assertEquals("Classic margarita pizza", sabores.getDescricao());
    }

    @Test
    public void testValorAdicional() {
        assertEquals(2.0, sabores.getValorAdicional(), 0.01);
    }

    @Test
    public void testIngredientes() {
        assertNotNull(sabores.getIngredientes());
        assertEquals(1, sabores.getIngredientes().size());
        assertEquals("Tomato Sauce", sabores.getIngredientes().get(0).getNomeIngrediente());
    }

    @Test
    public void testPedidosPizza() {
        assertNotNull(sabores.getPedidosPizza());
        assertEquals(1, sabores.getPedidosPizza().size());
        assertEquals(2, sabores.getPedidosPizza().get(0).getQtdePedida());
    }
}
