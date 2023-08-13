package com.mensal.sliceCtrl.repository;

import com.mensal.sliceCtrl.entity.Pizzas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PizzaRepository extends JpaRepository<Pizzas,Long> {
    @Query("SELECT p FROM Pizzas p WHERE p.nomeProduto = :nomeProduto")
    Pizzas findByNome(@Param("nomeProduto") String nomeProduto);

//    @Query("SELECT p FROM Pizza p WHERE p.sabor.nomeSabor = :nomeSabor")
//    Pizza findByNomeSabor(@Param("nomeSabor") String nomeSabor);

//    @Query("SELECT p FROM Pizza p WHERE p.tamanho = :tamanho")
//    List<Pizza> findByTamanho(@Param("tamanhoName") Tamanho tamanho);

//    @Query("SELECT p FROM Pizza p WHERE p.disponivel = :true")
//    List<Pizza> findByDisponivel(@Param("disponivel") boolean disponivel);
}
