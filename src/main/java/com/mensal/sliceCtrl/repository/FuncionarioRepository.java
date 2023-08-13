package com.mensal.sliceCtrl.repository;

import com.mensal.sliceCtrl.DTO.FuncionarioDTO;
import com.mensal.sliceCtrl.entity.Cliente;
import com.mensal.sliceCtrl.entity.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FuncionarioRepository extends JpaRepository<Funcionario,Long> {

    @Query("SELECT f FROM Funcionario f WHERE f.nome = :nome")
    List<Funcionario> findByNome(@Param("nome") String nome);

    @Query("SELECT f FROM Funcionario f WHERE f.cpf = :cpf")
    Funcionario findByCpf(String cpf);
}
