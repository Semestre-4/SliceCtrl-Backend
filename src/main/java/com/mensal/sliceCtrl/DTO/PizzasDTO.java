package com.mensal.sliceCtrl.DTO;

import com.mensal.sliceCtrl.entity.enums.Categorias;
import com.mensal.sliceCtrl.entity.enums.Tamanho;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter @Setter
public class PizzasDTO extends AbstractEntityDTO{

    private Tamanho tamanho;
    private String observacao;
    private String nomeProduto;
    private Categorias categoria;
    private List<SaboresDTO> sabor;
    private Integer qtdeEstoque;
    private Integer qtdePedido;
    private BigDecimal preco;
    private String descricao;
    private boolean disponivel;

}
