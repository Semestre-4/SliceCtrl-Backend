package com.mensal.sliceCtrl.entity;

import com.mensal.sliceCtrl.entity.enums.FormasDePagamento;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "pagamentos",schema = "public")
@Getter
@Setter
public class Pagamentos extends AbstractEntity {

    @OneToOne
    @JoinColumn(name = "pedido_id")
    private Pedidos pedidos;

    @NotNull(message = "A forma de pagamento não pode ser nula")
    @Column(name = "formasDePagamento", nullable = false)
    private FormasDePagamento formasDePagamento;

    @Column(name = "data_pagamento")
    private LocalDateTime dataPagamento;

    @Column(name = "is_pago", nullable = false)
    private boolean isPago;

    @PrePersist
    private void updatePagamentoOnPersist() {
        this.dataPagamento = LocalDateTime.now();
    }

}
