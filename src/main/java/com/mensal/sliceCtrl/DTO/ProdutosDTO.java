package com.mensal.sliceCtrl.DTO;

import com.mensal.sliceCtrl.entity.enums.Categorias;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
public class ProdutosDTO extends AbstractEntityDTO {

    private String nomeProduto;
    private Categorias categoria;
    private Integer qtdeEstoque;
    private Integer qtdePedido;
    private BigDecimal preco;
    private String descricao;
    private boolean disponivel;

}
