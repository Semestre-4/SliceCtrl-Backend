package com.mensal.sliceCtrl.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "pedidos",schema = "public")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "pedido")
    private Pagamento pagamento;

}
