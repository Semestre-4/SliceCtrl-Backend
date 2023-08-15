package com.mensal.sliceCtrl.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mensal.sliceCtrl.entity.enums.Categorias;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "produtos", schema = "public")
@Getter
@Setter
public class Produtos extends AbstractEntity{

    @NotNull(message = "É obrigatorio informar o nome do produto")
    @NotBlank(message = "É obrigatorio informar o nome do produto")
    @Column(name = "nome_produto", nullable = false, unique = true)
    @Size(min = 2, max = 100, message = "o produto pode ter entre 2 e 100 caracteres")
    String nomeProduto;

    @NotNull(message = "É obrigatorio informar a categoria")
    @Column(name = "categoria", nullable = false)
    @Enumerated(EnumType.STRING)
    private Categorias categoria;

    @NotNull(message = "É obrigatorio informar a quantidade de estoque")
    @Column(name = "qtde_estoque", nullable = false)
    private Integer qtdeEstoque;

    @NotNull(message = "É obrigatorio informar o preço do produto")
    @Column(name = "preco_produto", nullable = false)
    private Double preco;

    @Column(name = "is_disponivel", nullable = false)
    private boolean disponivel;

    @JsonIgnore
    @ManyToMany(mappedBy = "produtos")
    private List<Pedidos> pedidos = new ArrayList<>();

}
