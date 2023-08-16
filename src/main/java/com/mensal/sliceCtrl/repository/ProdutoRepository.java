package com.mensal.sliceCtrl.repository;

import com.mensal.sliceCtrl.entity.Produtos;
import com.mensal.sliceCtrl.entity.Sabores;
import com.mensal.sliceCtrl.entity.enums.Categorias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Arrays;
import java.util.List;

public interface ProdutoRepository  extends JpaRepository<Produtos, Long> {
    @Query("from Produtos where nomeProduto Like :nomeProduto")
    public Produtos findByNome(@Param("nomeProduto") final String nomeProduto);

    @Query("FROM Produtos p WHERE p.categoria = :categoria")
    List<Produtos> findByCategoria(@Param("categoria") Categorias categoria);

    @Query("SELECT p FROM Produtos p WHERE p.disponivel = true")
    List<Produtos> findByDisponivelTrue();
}
