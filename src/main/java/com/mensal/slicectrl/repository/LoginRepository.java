package com.mensal.slicectrl.repository;

import com.mensal.slicectrl.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LoginRepository extends JpaRepository<Usuario, Long>{
    Optional<Usuario> findByCpf(String cpf);
    boolean existsByCpfAndPassword(String cpf, String password);

}
