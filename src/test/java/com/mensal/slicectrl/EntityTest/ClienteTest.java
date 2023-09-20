package com.mensal.slicectrl.EntityTest;

import com.mensal.slicectrl.entity.Clientes;
import com.mensal.slicectrl.entity.Enderecos;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class ClienteTest {

    Enderecos endereco =
            new Enderecos("rua", 123, "complemento", "bairro", "cidade", "PR", "Brazil", "85857730");
    private List<Enderecos> enderecos = new ArrayList<>();
    Clientes cliente = new Clientes("John","0202938920","92020808320","sdjnkjds@kdjee.dd",enderecos);

    @BeforeEach
    void setUp(){
        endereco.setId(1L);
        cliente.setId(1L);
        enderecos.add(endereco);
    }

    @Test
    void getId() {
        long id = cliente.getId();
        Assertions.assertEquals(1L, id, 0);
    }

    @Test
    void getNome() {
        String nome = cliente.getNome();
        Assertions.assertEquals("John", nome);

    }

    @Test
    void getTelefone() {
        String tel = cliente.getTelefone();
        Assertions.assertEquals("92020808320", tel);

    }

    @Test
    void getEndereco() {
        long id = cliente.getEnderecos().get(0).getId();
        Assertions.assertEquals(1L, id, 0);
    }

    @Test
    void setEndereco() {

        List<Enderecos> endereco1 = new ArrayList<>();
        Enderecos added = new Enderecos ("rua", 123, "complemento", "bairro", "cidade", "PR", "Brazil", "85857730");
        added.setId(2L);
        endereco1.add(added);
        cliente.setEnderecos(endereco1);
        long id = cliente.getEnderecos().get(0).getId();

        Assertions.assertEquals(2L, id, 0);
    }
}