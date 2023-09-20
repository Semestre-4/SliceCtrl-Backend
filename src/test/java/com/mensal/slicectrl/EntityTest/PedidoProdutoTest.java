package com.mensal.slicectrl.EntityTest;

import com.mensal.slicectrl.entity.PedidoProduto;
import com.mensal.slicectrl.entity.Pedidos;
import com.mensal.slicectrl.entity.Produtos;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PedidoProdutoTest {

    private PedidoProduto pedidoProduto;

    @BeforeEach
    public void setUp() {
        pedidoProduto = new PedidoProduto();
        pedidoProduto.setQtdePedida(5);

        Produtos produto = new Produtos();
        produto.setNomeProduto("Sample Product");
        pedidoProduto.setProduto(produto);

        Pedidos pedido = new Pedidos();
        pedidoProduto.setPedido(pedido);
    }

    @Test
    public void testQtdePedida() {
        assertEquals(5, pedidoProduto.getQtdePedida());
    }

    @Test
    public void testProduto() {
        assertNotNull(pedidoProduto.getProduto());
        assertEquals("Sample Product", pedidoProduto.getProduto().getNomeProduto());
    }

    @Test
    public void testPedido() {
        assertNotNull(pedidoProduto.getPedido());
    }
}
