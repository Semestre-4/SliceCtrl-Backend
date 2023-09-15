package com.mensal.slicectrl.repository;

import com.mensal.slicectrl.entity.Clientes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Clientes, Long> {
    @Query("SELECT c FROM Clientes c WHERE c.nome = :nome")
    List<Clientes> findByNome(@Param("nome") String nome);

    @Query("SELECT c FROM Clientes c WHERE c.cpf = :cpf")
    Clientes findByCpf(@Param("cpf") String cpf);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Clientes c WHERE c.cpf = ?1")
    boolean existsByCpf(String cpf);
}
