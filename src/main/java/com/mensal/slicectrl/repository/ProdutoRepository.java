package com.mensal.slicectrl.repository;

import com.mensal.slicectrl.entity.Pizzas;
import com.mensal.slicectrl.entity.Produtos;
import com.mensal.slicectrl.entity.enums.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProdutoRepository  extends JpaRepository<Produtos, Long> {
    @Query("from Produtos where nomeProduto Like :nomeProduto")
    public Produtos findByNome(@Param("nomeProduto") final String nomeProduto);

    @Query("FROM Produtos p WHERE p.categoria = :categoria")
    List<Produtos> findByCategoria(@Param("categoria") Categoria categoria);

    @Query("SELECT p FROM Produtos p WHERE p.disponivel = true")
    List<Produtos> findByDisponivelTrue();

    @Query("SELECT p FROM Produtos p WHERE p.ativo = :ativo")
    List<Produtos> findByAtivo(@Param("ativo") boolean ativo);

}
