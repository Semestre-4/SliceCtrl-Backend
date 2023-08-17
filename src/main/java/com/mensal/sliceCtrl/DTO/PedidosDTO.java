package com.mensal.sliceCtrl.DTO;

import com.fasterxml.jackson.annotation.JsonView;
import com.mensal.sliceCtrl.entity.enums.Status;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PedidosDTO extends AbstractEntityDTO {

    @NotNull(message = "É obrigatorio informar o cliente associado")
    private ClientesDTO cliente;
    @NotNull(message = "É obrigatorio informar o funcionario associado")
    private FuncionariosDTO funcionario;
    private List<PedidoProdutoDTO> produtos = new ArrayList<>();
    private List<PedidoPizzaDTO> pizzas = new ArrayList<>();
    private PagamentoDTO pagamentoDTO;
    @NotNull(message = "O status do pedido é obrigatório")
    private Status status;
    private boolean forEntrega;
    private boolean forTakeaway;
    private boolean forDineIn;
    private boolean isPago;
}
