package com.mensal.sliceCtrl.DTO;


import com.mensal.sliceCtrl.entity.Pedidos;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoProdutoDTO extends AbstractEntityDTO {

    @NotNull(message = "O produto não pode ser nulo")
    private ProdutosDTO produto;
    private PedidosDTO pedido;
    @Min(value = 1, message = "A quantidade pedida deve ser pelo menos 1")
    private int qtdePedida;

}
