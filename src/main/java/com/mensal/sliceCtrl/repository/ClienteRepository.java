package com.mensal.sliceCtrl.repository;

import com.mensal.sliceCtrl.entity.Clientes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Clientes, Long> {
    @Query("SELECT c FROM Clientes c WHERE c.nome = :nome")
    List<Clientes> findByNome(@Param("nome") String nome);

    @Query("SELECT c FROM Clientes c WHERE c.cpf = :cpf")
    Clientes findByCpf(@Param("cpf") String cpf);
}
