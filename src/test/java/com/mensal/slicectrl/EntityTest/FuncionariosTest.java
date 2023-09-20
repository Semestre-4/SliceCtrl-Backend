package com.mensal.slicectrl.EntityTest;

import com.mensal.slicectrl.entity.Funcionarios;
import com.mensal.slicectrl.entity.Pedidos;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class FuncionariosTest {

    private Funcionarios funcionario;

    @BeforeEach
    public void setUp() {
        funcionario = new Funcionarios();
        funcionario.setNome("John Doe");
        funcionario.setCpf("123456789");
        funcionario.setTelefone("555-555-5555");
        funcionario.setSalario(new BigDecimal("50000.00"));

        // Initialize the list of pedidos
        List<Pedidos> pedidos = new ArrayList<>();
        Pedidos pedido1 = new Pedidos();
        pedido1.setFuncionario(funcionario);
        pedidos.add(pedido1);
        funcionario.setPedidos(pedidos);
    }

    @Test
    public void testNome() {
        assertEquals("John Doe", funcionario.getNome());
    }

    @Test
    public void testCpf() {
        assertEquals("123456789", funcionario.getCpf());
    }

    @Test
    public void testTelefone() {
        assertEquals("555-555-5555", funcionario.getTelefone());
    }

    @Test
    public void testSalario() {
        assertEquals(new BigDecimal("50000.00"), funcionario.getSalario());
    }

    @Test
    public void testPedidos() {
        List<Pedidos> pedidos = funcionario.getPedidos();
        assertNotNull(pedidos);
        assertEquals(1, pedidos.size());
    }
}
