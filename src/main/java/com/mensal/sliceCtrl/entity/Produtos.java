package com.mensal.sliceCtrl.entity;

import com.mensal.sliceCtrl.entity.enums.Categorias;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Table(name = "produtos", schema = "public")
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


    @Column(name = "qtde_pedido")
    private Integer qtdePedido;

    @NotNull(message = "É obrigatorio informar o preço do produto")
    @Column(name = "preco_produto", nullable = false)
    private Double preco;

    @Column(name = "descricao_sabor", nullable = true)
    private String descricao;

    @Column(name = "is_disponivel", nullable = false)
    private boolean disponivel;

}
