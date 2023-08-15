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

    @Column(name = "codigo_pedido", nullable = false, unique = true)
    private String codigo;

    @ManyToMany
    @JoinTable(
            name = "pedido_produto",
            joinColumns = @JoinColumn(name = "pedido_id"),
            inverseJoinColumns = @JoinColumn(name = "produto_id")
    )
    @JsonIgnoreProperties("pedidos")
    private List<Produtos> produtos = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "pedido_pizza",
            joinColumns = @JoinColumn(name = "pedido_id"),
            inverseJoinColumns = @JoinColumn(name = "pizza_id")
    )
    @JsonIgnoreProperties("pedidos")
    private List<Pizzas> pizzas = new ArrayList<>();

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

    @PrePersist
    public void generateCodigoPedido() {
        this.codigo = generateCodigoPedidoLogic();
    }

    private String generateCodigoPedidoLogic() {
        Long sequenceNumber = getId();
        String sequencePart = sequenceNumber != null ? sequenceNumber.toString() : "UNKNOWN"; // Provide a default value

        String randomPart = generateRandomPart();

        return String.format("PEDIDO-%s-%s", sequencePart, randomPart);
    }


    private String generateRandomPart() {
        // Generate a random part using a random number generator
        StringBuilder randomPart = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            randomPart.append(random.nextInt(10));
        }
        return randomPart.toString();
    }
}