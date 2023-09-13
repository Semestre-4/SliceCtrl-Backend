package com.mensal.slicectrl.repository;

import com.mensal.slicectrl.entity.Enderecos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnderecoRepository  extends JpaRepository<Enderecos, Long> {

    @Query("from Enderecos where cep Like :cep")
    public List<Enderecos> findByCep(@Param("cep") final String cep);


}
