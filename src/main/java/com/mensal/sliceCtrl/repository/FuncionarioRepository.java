package com.mensal.sliceCtrl.repository;

import com.mensal.sliceCtrl.entity.Funcionarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FuncionarioRepository extends JpaRepository<Funcionarios,Long> {

    @Query("SELECT f FROM Funcionario f WHERE f.nome = :nome")
    List<Funcionarios> findByNome(@Param("nome") String nome);

    @Query("SELECT f FROM Funcionario f WHERE f.cpf = :cpf")
    Funcionarios findByCpf(String cpf);
}
