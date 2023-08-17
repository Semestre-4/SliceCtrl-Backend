package com.mensal.sliceCtrl.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.mensal.sliceCtrl.entity.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Entity
@Table(name = "pedidos", schema = "public")
@Getter
@Setter
public class Pedidos extends AbstractEntity{

    @ManyToOne
    @JoinColumn(name = "cliente_id", referencedColumnName = "id", nullable = false)
    @JsonIgnoreProperties("pedidos")
    private Clientes cliente;

    @ManyToOne
    @JoinColumn(name = "funcionario_id", nullable = false)
    @JsonIgnoreProperties("pedidos")
    private Funcionarios funcionario;

    @OneToMany(mappedBy = "pedido",cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JsonIgnoreProperties("pedido")
    private List<PedidoProduto> produtos = new ArrayList<>();


    @OneToMany(mappedBy = "pedido",cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JsonIgnoreProperties("pedido")
    private List<PedidoPizza> pizzas = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "pagamento_id")
    private Pagamento pagamento;

    @Column(name = "valor_pedido")
    private BigDecimal valorPedido;

    @Column(name = "valor_entrega")
    private BigDecimal valorEntrega;

    @Column(name = "valor_total")
    private BigDecimal valorTotal;

    @Column(name = "status_enum", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "for_entrega")
    private boolean forEntrega;

    @Column(name = "for_takeaway")
    private boolean forTakeaway;

    @Column(name = "for_dineIn")
    private boolean forDineIn;

}