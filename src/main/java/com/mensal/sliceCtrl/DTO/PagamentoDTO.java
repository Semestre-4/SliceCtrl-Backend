package com.mensal.sliceCtrl.DTO;

import com.mensal.sliceCtrl.entity.enums.FormasDePagamento;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PagamentoDTO {
    private Long pedidoId;
    @NotNull(message = "A forma de pagamento não pode ser nula")
    private FormasDePagamento formasDePagamento;

    private LocalDateTime dataPagamento;

    private boolean isPago;
}
