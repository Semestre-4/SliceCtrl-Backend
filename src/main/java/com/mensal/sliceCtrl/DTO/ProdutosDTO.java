package com.mensal.sliceCtrl.DTO;

import com.mensal.sliceCtrl.entity.enums.Categorias;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
public class ProdutosDTO extends AbstractEntityDTO {
    @NotNull(message = "É obrigatorio informar o nome do produto")
    @NotBlank(message = "É obrigatorio informar o nome do produto")
    @Column(name = "nome_produto", nullable = false, unique = true)
    @Size(min = 2, max = 100, message = "o produto pode ter entre 2 e 100 caracteres")
    private String nomeProduto;

    @NotNull(message = "É obrigatorio informar a categoria")
    private Categorias categoria;

    @NotNull(message = "É obrigatorio informar a quantidade de estoque")
    private Integer qtdeEstoque;

    @NotNull(message = "É obrigatorio informar a qtde do produto")
    private Integer qtdePedido;

    @NotNull(message = "É obrigatorio informar o preço do produto")
    private BigDecimal preco;
    private String descricao;

}
