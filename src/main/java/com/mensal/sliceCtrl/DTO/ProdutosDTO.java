package com.mensal.sliceCtrl.DTO;

import com.mensal.sliceCtrl.entity.AbstractEntity;
import com.mensal.sliceCtrl.entity.enums.Categorias;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

public class ProdutosDTO extends AbstractEntityDTO {

    String nomeProduto;

    private Categorias categoria;


    private Integer qtdeEstoque;


    private Integer qtdePedido;

    private Double preco;

    private String descricao;

    private boolean disponivel;

    public ProdutosDTO() {
    }

    public ProdutosDTO(String nomeProduto, Categorias categoria, Integer qtdeEstoque, Integer qtdePedido, Double preco, boolean disponivel) {
        this.nomeProduto = nomeProduto;
        this.categoria = categoria;
        this.qtdeEstoque = qtdeEstoque;
        this.qtdePedido = qtdePedido;
        this.preco = preco;
        this.disponivel = disponivel;
    }

    public ProdutosDTO(String nomeProduto, Categorias categoria, Integer qtdeEstoque, Integer qtdePedido, Double preco, String descricao, boolean disponivel) {
        this.nomeProduto = nomeProduto;
        this.categoria = categoria;
        this.qtdeEstoque = qtdeEstoque;
        this.qtdePedido = qtdePedido;
        this.preco = preco;
        this.descricao = descricao;
        this.disponivel = disponivel;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public Categorias getCategoria() {
        return categoria;
    }

    public void setCategoria(Categorias categoria) {
        this.categoria = categoria;
    }

    public Integer getQtdeEstoque() {
        return qtdeEstoque;
    }

    public void setQtdeEstoque(Integer qtdeEstoque) {
        this.qtdeEstoque = qtdeEstoque;
    }

    public Integer getQtdePedido() {
        return qtdePedido;
    }

    public void setQtdePedido(Integer qtdePedido) {
        this.qtdePedido = qtdePedido;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }
}
