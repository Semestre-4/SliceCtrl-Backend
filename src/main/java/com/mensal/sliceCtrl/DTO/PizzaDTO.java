package com.mensal.sliceCtrl.DTO;

import com.mensal.sliceCtrl.entity.enums.Tamanho;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class PizzaDTO extends ProdutosDTO {

    @NotBlank(message = "O tamanho da pizza é obrigatório")
    private Tamanho tamanho;

    private String observacao;

    private List<PedidoDTO> pedidos;

    private List<SaboresDTO> sabor;
}
