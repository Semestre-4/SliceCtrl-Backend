package com.mensal.slicectrl.repository;

import com.mensal.slicectrl.entity.Ingredientes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredienteRepository  extends JpaRepository<Ingredientes, Long> {

    @Query("from Ingredientes where nomeIngrediente = :nomeIngrediente")
    public Ingredientes findByNome(@Param("nomeIngrediente") final String nomeIngrediente);

}
