package com.mensal.sliceCtrl.DTO;

import com.mensal.sliceCtrl.entity.enums.FormasDePagamento;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class PagamentosDTO {
    private Long pedidoId;
    @NotNull(message = "A forma de pagamento não pode ser nula")
    private FormasDePagamento formasDePagamento;
    private LocalDateTime dataPagamento;
    private boolean isPago;
}
