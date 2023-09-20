package com.mensal.slicectrl.EntityTest;

import com.mensal.slicectrl.entity.PedidoProduto;
import com.mensal.slicectrl.entity.Produtos;
import com.mensal.slicectrl.entity.enums.Categoria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class ProdutosTest {

    private Produtos produtos;

    @BeforeEach
    public void setUp() {
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
    public void testNomeProduto() {
        assertEquals("Pizza Margherita", produtos.getNomeProduto());
    }

    @Test
    public void testCategoria() {
        assertEquals(Categoria.BEBIDAS, produtos.getCategoria());
    }

    @Test
    public void testQtdeEstoque() {
        assertEquals(10, produtos.getQtdeEstoque());
    }

    @Test
    public void testPreco() {
        assertEquals(12.99, produtos.getPreco(), 0.01);
    }

    @Test
    public void testDisponivel() {
        assertTrue(produtos.isDisponivel());
    }

    @Test
    public void testPedidos() {
        assertNotNull(produtos.getPedidos());
        assertEquals(1, produtos.getPedidos().size());
        assertEquals(2, produtos.getPedidos().get(0).getQtdePedida());
    }
}
