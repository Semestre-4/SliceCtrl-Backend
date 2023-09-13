package com.mensal.slicectrl.repository;

import com.mensal.slicectrl.entity.Sabores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SaboresRepository  extends JpaRepository<Sabores, Long> {

    @Query("from Sabores where nomeSabor = :nomeSabor")
    public Sabores findByNome(@Param("nomeSabor") final String nomeSabor);

    @Query("SELECT s FROM Sabores s WHERE s.nomeSabor = :nomeSabor")
    Optional<Sabores> findByNomeSabor(String nomeSabor);
}
