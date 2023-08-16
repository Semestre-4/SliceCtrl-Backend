package com.mensal.sliceCtrl.repository;

import com.mensal.sliceCtrl.entity.Funcionarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FuncionarioRepository extends JpaRepository<Funcionarios,Long> {

    @Query("SELECT f FROM Funcionarios f WHERE f.nome = :nome")
    List<Funcionarios> findByNome(@Param("nome") String nome);

    @Query("SELECT f FROM Funcionarios f WHERE f.cpf = :cpf")
    Funcionarios findByCpf(String cpf);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Funcionarios c WHERE c.cpf = ?1")
    boolean existsByCpf(String cpf);

    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END FROM Funcionarios f WHERE f.cpf = ?1 AND f.id <> ?2")
    boolean existsByCpfAndIdNot(String cpf, Long id);
}
