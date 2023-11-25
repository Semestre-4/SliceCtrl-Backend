package com.mensal.slicectrl.repository;

import com.mensal.slicectrl.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {

    @Query("SELECT f FROM Usuario f WHERE f.nome = :nome")
    List<Usuario> findByNome(@Param("nome") String nome);

    @Query("SELECT f FROM Usuario f WHERE f.ativo = :ativo")
    List<Usuario> findByAtivo(@Param("ativo") boolean ativo);


    @Query("SELECT f FROM Usuario f WHERE f.cpf = :cpf")
    Usuario findByCpf(String cpf);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Usuario c WHERE c.cpf = ?1")
    boolean existsByCpf(String cpf);

    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END FROM Usuario f WHERE f.cpf = ?1 AND f.id <> ?2")
    boolean existsByCpfAndIdNot(String cpf, Long id);
}
