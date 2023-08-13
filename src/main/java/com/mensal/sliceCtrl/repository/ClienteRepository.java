package com.mensal.sliceCtrl.repository;

import com.mensal.sliceCtrl.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    @Query("SELECT c FROM Cliente c WHERE c.nome = :nome")
    List<Cliente> findByNome(@Param("nome") String nome);

    @Query("SELECT c FROM Cliente c WHERE c.cpf = :cpf")
    Cliente findByCpf(@Param("cpf") String cpf);
}
