package com.mensal.sliceCtrl.DTO;

import com.mensal.sliceCtrl.entity.Pedidos;
import com.mensal.sliceCtrl.entity.Sabores;
import com.mensal.sliceCtrl.entity.enums.Categorias;
import com.mensal.sliceCtrl.entity.enums.Tamanho;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class PizzasDTO extends AbstractEntityDTO{

    @Enumerated(EnumType.STRING)
    @NotNull(message = "É obrigatorio informar o tamanho da pizza")
    private Tamanho tamanho;
    private String observacao;
    @NotNull(message = "É obrigatorio informar o preço do produto")
    private Double preco;
    @NotNull(message = "É obrigatorio informar a quantidade de estoque")
    @Column(name = "qtde_estoque", nullable = false)
    private Integer qtdeEstoque;
    @NotNull(message = "É obrigatorio informar pelo menos 1 sabor")
    private List<SaboresDTO> sabor;

}
