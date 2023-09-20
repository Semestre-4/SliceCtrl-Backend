package com.mensal.slicectrl.EntityTest;

import com.mensal.slicectrl.entity.PedidoProduto;
import com.mensal.slicectrl.entity.Produtos;
import com.mensal.slicectrl.entity.enums.Categoria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

class ProdutosTest {

    private Produtos produtos;

    @BeforeEach
    void setUp() {
        produtos = new Produtos();
        produtos.setNomeProduto("Pizza Margherita");
        produtos.setCategoria(Categoria.BEBIDAS);
        produtos.setQtdeEstoque(10);
        produtos.setPreco(12.99);
        produtos.setDisponivel(true);

        List<PedidoProduto> pedidos = new ArrayList<>();
        PedidoProduto pedido1 = new PedidoProduto();
        pedido1.setQtdePedida(2);
        pedido1.setProduto(produtos);
        pedidos.add(pedido1);
        produtos.setPedidos(pedidos);
    }

    @Test
    void testNomeProduto() {
        assertEquals("Pizza Margherita", produtos.getNomeProduto());
    }

    @Test
    void testCategoria() {
        assertEquals(Categoria.BEBIDAS, produtos.getCategoria());
    }

    @Test
    void testQtdeEstoque() {
        assertEquals(10, produtos.getQtdeEstoque());
    }

    @Test
    void testPreco() {
        assertEquals(12.99, produtos.getPreco(), 0.01);
    }

    @Test
    void testDisponivel() {
        assertTrue(produtos.isDisponivel());
    }

    @Test
    void testPedidos() {
        assertNotNull(produtos.getPedidos());
        assertEquals(1, produtos.getPedidos().size());
        assertEquals(2, produtos.getPedidos().get(0).getQtdePedida());
    }
}
