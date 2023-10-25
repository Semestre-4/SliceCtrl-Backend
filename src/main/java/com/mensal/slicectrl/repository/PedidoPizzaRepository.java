package com.mensal.slicectrl.repository;

import com.mensal.slicectrl.entity.PedidoPizza;
import com.mensal.slicectrl.entity.Sabores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PedidoPizzaRepository extends JpaRepository<PedidoPizza, Long> {

    @Query("SELECT s, COUNT(pp) as totalOrders " +
            "FROM PedidoPizza pp " +
            "JOIN pp.sabores s " +
            "GROUP BY s " +
            "ORDER BY totalOrders DESC")
    List<Object[]> findMostUsedSabores();

}

