package com.mensal.sliceCtrl.DTO;

import com.mensal.sliceCtrl.entity.enums.Tamanho;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class PizzasDTO extends ProdutosDTO {

    @NotBlank(message = "O tamanho da pizza é obrigatório")
    private Tamanho tamanho;
    private String observacao;
    private List<PedidosDTO> pedidos;
    private List<SaboresDTO> sabor;
}
