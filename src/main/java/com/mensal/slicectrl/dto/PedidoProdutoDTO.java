package com.mensal.slicectrl.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoProdutoDTO extends AbstractEntityDTO {

    @NotNull(message = "O produto não pode ser nulo")
    @JsonIgnoreProperties("pedidos")
    private ProdutosDTO produto;
    @JsonIgnoreProperties("produtos")
    private PedidosDTO pedido;
    @Min(value = 1, message = "A quantidade pedida deve ser pelo menos 1")
    private int qtdePedida;

}
