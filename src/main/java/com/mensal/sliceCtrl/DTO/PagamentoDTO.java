package com.mensal.sliceCtrl.DTO;

import com.mensal.sliceCtrl.entity.enums.FormasDePagamento;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagamentoDTO extends AbstractEntityDTO {
//    private Long pedidoId;
    @NotNull(message = "A forma de pagamento não pode ser nula")
    private FormasDePagamento formasDePagamento;

    private boolean isPago;
}
