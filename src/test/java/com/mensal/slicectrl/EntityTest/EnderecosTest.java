package com.mensal.slicectrl.EntityTest;

import com.mensal.slicectrl.entity.Clientes;
import com.mensal.slicectrl.entity.Enderecos;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

class EnderecosTest {

    private Enderecos enderecos;

    @BeforeEach
    void setUp() {
        enderecos = new Enderecos();
        enderecos.setRua("123 Main St");
        enderecos.setNumero(456);
        enderecos.setComplemento("Apt 101");
        enderecos.setBairro("Downtown");
        enderecos.setCidade("Cityville");
        enderecos.setEstado("ST");
        enderecos.setPais("Country");
        enderecos.setCep("12345-678");

        List<Clientes> clientes = new ArrayList<>();
        Clientes cliente1 = new Clientes();
        cliente1.setNome("John Doe");
        cliente1.getEnderecos().add(enderecos);
        clientes.add(cliente1);
        enderecos.setClientes(clientes);
    }

    @Test
    void testRua() {
        assertEquals("123 Main St", enderecos.getRua());
    }

    @Test
    void testNumero() {
        assertEquals(456, enderecos.getNumero());
    }

    @Test
    void testComplemento() {
        assertEquals("Apt 101", enderecos.getComplemento());
    }

    @Test
    void testBairro() {
        assertEquals("Downtown", enderecos.getBairro());
    }

    @Test
    void testCidade() {
        assertEquals("Cityville", enderecos.getCidade());
    }

    @Test
    void testEstado() {
        assertEquals("ST", enderecos.getEstado());
    }

    @Test
    void testPais() {
        assertEquals("Country", enderecos.getPais());
    }

    @Test
    void testCep() {
        assertEquals("12345-678", enderecos.getCep());
    }

    @Test
    void testClientes() {
        assertNotNull(enderecos.getClientes());
        assertEquals(1, enderecos.getClientes().size());
        assertEquals("John Doe", enderecos.getClientes().get(0).getNome());
    }
}
