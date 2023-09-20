package com.mensal.slicectrl.EntityTest;

import com.mensal.slicectrl.entity.Ingredientes;
import com.mensal.slicectrl.entity.Sabores;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class IngredientesTest {

    private Ingredientes ingredientes;

    @BeforeEach
    public void setUp() {
        ingredientes = new Ingredientes();
        ingredientes.setNomeIngrediente("Tomato Sauce");
        ingredientes.setQtdeIngrediente(0.5);

        List<Sabores> sabores = new ArrayList<>();
        Sabores sabor1 = new Sabores();
        sabor1.setNomeSabor("Margarita");
        sabor1.getIngredientes().add(ingredientes);
        sabores.add(sabor1);
        ingredientes.setSabores(sabores);
    }

    @Test
    public void testNomeIngrediente() {
        assertEquals("Tomato Sauce", ingredientes.getNomeIngrediente());
    }

    @Test
    public void testQtdeIngrediente() {
        assertEquals(0.5, ingredientes.getQtdeIngrediente(), 0.01); // Using delta for double comparison
    }

    @Test
    public void testSabores() {
        assertNotNull(ingredientes.getSabores());
        assertEquals(1, ingredientes.getSabores().size());
        assertEquals("Margarita", ingredientes.getSabores().get(0).getNomeSabor());
    }
}
