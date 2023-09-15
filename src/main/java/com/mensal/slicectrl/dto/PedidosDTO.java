package com.mensal.slicectrl.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mensal.slicectrl.entity.enums.FormaDeEntrega;
import com.mensal.slicectrl.entity.enums.Status;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PedidosDTO extends AbstractEntityDTO {

    @NotNull(message = "É obrigatório informar o cliente associado")
    @Valid
    private ClientesDTO cliente;

    @NotNull(message = "É obrigatório informar o funcionário associado")
    @Valid
    private FuncionariosDTO funcionario;

    @NotEmpty(message = "A lista de produtos não pode estar vazia")
    @JsonIgnoreProperties("pedido")
    private List<@Valid PedidoProdutoDTO> produtos = new ArrayList<>();

    @NotEmpty(message = "A lista de pizzas não pode estar vazia")
    @JsonIgnoreProperties("pedido")
    private List<@Valid PedidoPizzaDTO> pizzas = new ArrayList<>();

    private PagamentoDTO pagamentoDTO;

    @NotNull(message = "O status não pode ser nulo")
    private Status status;

    @NotNull(message = "A forma de entrega não pode ser nula")
    private FormaDeEntrega formaDeEntrega;

    @AssertTrue(message = "O pagamento deve estar marcado como pago se o status for PAGO")
    private boolean isPago;

}
