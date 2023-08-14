package com.mensal.sliceCtrl.repository;

import com.mensal.sliceCtrl.entity.Pizzas;
import com.mensal.sliceCtrl.entity.enums.Tamanho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PizzaRepository extends JpaRepository<Pizzas,Long> {
    @Query("SELECT p FROM Pizzas p WHERE p.nomeProduto = :nomeProduto")
    Pizzas findByNome(@Param("nomeProduto") String nomeProduto);

    @Query("SELECT p FROM Pizzas p JOIN p.sabor s WHERE s.nomeSabor = :nomeSabor")
    Pizzas findByNomeSabor(@Param("nomeSabor") String nomeSabor);
    @Query("SELECT p FROM Pizzas p WHERE p.tamanho = :tamanho")
    List<Pizzas> findByTamanho(@Param("tamanho") Tamanho tamanho);

    @Query("SELECT p FROM Pizzas p WHERE p.disponivel = true")
    List<Pizzas> findByDisponivelTrue();

}
