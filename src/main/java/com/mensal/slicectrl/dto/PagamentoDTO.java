package com.mensal.slicectrl.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mensal.slicectrl.entity.Pedidos;
import com.mensal.slicectrl.entity.enums.FormasDePagamento;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagamentoDTO extends AbstractEntityDTO {
    @JsonIgnore
    private Pedidos pedido;
    @NotNull(message = "A forma de pagamento não pode ser nula")
    private FormasDePagamento formasDePagamento;
    private boolean isPago;
}
