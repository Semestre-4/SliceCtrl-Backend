package com.mensal.slicectrl.EntityTest;

import com.mensal.slicectrl.entity.Usuario;
import com.mensal.slicectrl.entity.Pedidos;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

class UsuarioTest {

    private Usuario usuario;

    @BeforeEach
    public void setUp() {
        usuario = new Usuario();
        usuario.setNome("John Doe");
        usuario.setCpf("123456789");
        usuario.setTelefone("555-555-5555");
        usuario.setSalario(new BigDecimal("50000.00"));

        // Initialize the list of pedidos
        List<Pedidos> pedidos = new ArrayList<>();
        Pedidos pedido1 = new Pedidos();
        pedido1.setUsuario(usuario);
        pedidos.add(pedido1);
        usuario.setPedidos(pedidos);
    }

    @Test
    void testNome() {
        assertEquals("John Doe", usuario.getNome());
    }

    @Test
    void testCpf() {
        assertEquals("123456789", usuario.getCpf());
    }

    @Test
    void testTelefone() {
        assertEquals("555-555-5555", usuario.getTelefone());
    }

    @Test
    void testSalario() {
        assertEquals(new BigDecimal("50000.00"), usuario.getSalario());
    }

    @Test
    void testPedidos() {
        List<Pedidos> pedidos = usuario.getPedidos();
        assertNotNull(pedidos);
        assertEquals(1, pedidos.size());
    }
}
