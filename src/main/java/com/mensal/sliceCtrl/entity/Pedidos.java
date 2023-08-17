package com.mensal.sliceCtrl.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mensal.sliceCtrl.entity.enums.FormasDePagamento;
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

    @OneToMany(mappedBy = "pedido",fetch = FetchType.EAGER)
    @JsonIgnoreProperties("pedido")
    private List<PedidoProduto> produtos = new ArrayList<>();


    @OneToMany(mappedBy = "pedido",fetch = FetchType.EAGER)
    @JsonIgnoreProperties("pedido")
    private List<PedidoPizza> pizzas = new ArrayList<>();

    @NotNull(message = "O status do pedido é obrigatório")
    @Column(name = "status_enum", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @DecimalMin(value = "0.0", message = "O valor do pedido deve ser maior ou igual a 0")
    @Column(name = "valor_pedido")
    private BigDecimal valorPedido;

    @DecimalMin(value = "0.0", message = "O valor da entrega deve ser maior ou igual a 0")
    @Column(name = "valor_entrega")
    private BigDecimal valorEntrega;

    @DecimalMin(value = "0.0", message = "O valor total deve ser maior ou igual a 0")
    @Column(name = "valor_total")
    private BigDecimal valorTotal;

    @Column(name = "forma_pagemento",nullable = false)
    @NotNull(message = "A forma de pagamento do pedido é obrigatório")
    @Enumerated(EnumType.STRING)
    private FormasDePagamento formasDePagamento;

    @Column(name = "for_entrega")
    private boolean forEntrega;

    @Column(name = "for_takeaway")
    private boolean forTakeaway;

    @Column(name = "for_dineIn")
    private boolean forDineIn;

    @Column(name = "is_pago")
    private boolean isPago;

}