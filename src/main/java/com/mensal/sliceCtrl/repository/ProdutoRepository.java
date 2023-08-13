package com.mensal.sliceCtrl.repository;

import com.mensal.sliceCtrl.entity.Ingredientes;
import com.mensal.sliceCtrl.entity.Produtos;
import com.mensal.sliceCtrl.entity.Sabores;
import com.mensal.sliceCtrl.entity.enums.Categorias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository  extends JpaRepository<Produtos, Long> {
    @Query("from Produtos where nomeProduto Like :nomeProduto")
    public Produtos findByNome(@Param("nomeProduto") final String nomeProduto);

    @Query("from Produtos where categoria = :categoria")
    public Produtos findByCategoria(@Param("categoria") final String categoria);

    @Query("from Produtos where disponivel = :disponivel")
    public List<Produtos> findByDisponivel(@Param("disponivel") final Boolean disponivel);



}
