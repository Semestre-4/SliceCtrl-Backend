package com.mensal.slicectrl.repository;

import com.mensal.slicectrl.entity.Pizzas;
import com.mensal.slicectrl.entity.enums.Tamanho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PizzaRepository extends JpaRepository<Pizzas,Long> {
    @Query("SELECT p FROM Pizzas p WHERE p.tamanho = :tamanho")
    List<Pizzas> findByTamanho(@Param("tamanho") Tamanho tamanho);

    @Query("SELECT p FROM Pizzas p WHERE p.ativo = :ativo")
    List<Pizzas> findByAtivo(@Param("ativo") boolean ativo);

}
