package com.mensal.sliceCtrl.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "pedidos",schema = "public")
public class Pedido extends AbstractEntity {

    @OneToOne(mappedBy = "pedido")
    private Pagamento pagamento;

}
