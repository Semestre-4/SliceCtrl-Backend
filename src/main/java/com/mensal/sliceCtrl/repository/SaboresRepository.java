package com.mensal.sliceCtrl.repository;

import com.mensal.sliceCtrl.entity.Ingredientes;
import com.mensal.sliceCtrl.entity.Sabores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SaboresRepository  extends JpaRepository<Sabores, Long> {

    @Query("from Sabores where nomeSabor Like :nomeSabor")
    public Sabores findByNome(@Param("nomeSabor") final String nomeSabor);

}
