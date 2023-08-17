package com.mensal.sliceCtrl.entity;

import com.mensal.sliceCtrl.entity.enums.FormasDePagamento;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "pagamentos",schema = "public")
@Getter @Setter
public class Pagamento extends AbstractEntity {

    @OneToOne(mappedBy = "pagamento", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "pedido_id")
    private Pedidos pedido;

    @NotNull(message = "A forma de pagamento não pode ser nula")
    @Column(name = "formasDePagamento", nullable = false)
    private FormasDePagamento formasDePagamento;

    @Column(name = "is_pago", nullable = false)
    private boolean isPago;


}