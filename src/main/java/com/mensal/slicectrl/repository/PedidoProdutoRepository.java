package com.mensal.slicectrl.repository;

import com.mensal.slicectrl.entity.PedidoProduto;
import com.mensal.slicectrl.entity.Produtos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PedidoProdutoRepository extends JpaRepository<PedidoProduto, Long> {

    @Query("SELECT pp.produto, SUM(pp.qtdePedida) as totalQuantity " +
            "FROM PedidoProduto pp " +
            "GROUP BY pp.produto " +
            "ORDER BY totalQuantity DESC")
    List<Produtos> findMostUsedProducts();
}
