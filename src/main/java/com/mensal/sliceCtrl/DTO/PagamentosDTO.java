package com.mensal.sliceCtrl.DTO;

import com.mensal.sliceCtrl.entity.enums.FormasDePagamento;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class PagamentosDTO {
    private Long pedidoId;
    private FormasDePagamento formasDePagamento;
    private LocalDateTime dataPagamento;
    private boolean isPago;
}
