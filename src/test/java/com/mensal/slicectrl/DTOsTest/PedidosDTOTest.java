package com.mensal.slicectrl.DTOsTest;

import com.mensal.slicectrl.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.mensal.slicectrl.entity.enums.FormaDeEntrega;
import com.mensal.slicectrl.entity.enums.Status;

import java.util.ArrayList;

class PedidosDTOTest {

    private PedidosDTO pedidosDTO;

    @BeforeEach
    void setUp() {
        pedidosDTO = new PedidosDTO();
        pedidosDTO.setCliente(new ClientesDTO());
        pedidosDTO.setFuncionario(new FuncionariosDTO());
        pedidosDTO.setProdutos(new ArrayList<>());
        pedidosDTO.setPizzas(new ArrayList<>());
        pedidosDTO.setStatus(Status.PAGO);
        pedidosDTO.setFormaDeEntrega(FormaDeEntrega.ENTREGA);
    }

    @Test
    void testValidPedidosDTO() {
        pedidosDTO.getProdutos().add(new PedidoProdutoDTO());
        pedidosDTO.getPizzas().add(new PedidoPizzaDTO());

        assertDoesNotThrow(() -> validatePedidosDTO(pedidosDTO));
    }

    @Test
    void testInvalidPedidosDTO() {
        assertThrows(IllegalArgumentException.class, () -> validatePedidosDTO(pedidosDTO));
    }

    private void validatePedidosDTO(PedidosDTO dto) {
        if (dto.getCliente() == null || dto.getFuncionario() == null) {
            throw new IllegalArgumentException("Cliente and Funcionario must not be null");
        }

        if (dto.getProdutos().isEmpty() && dto.getPizzas().isEmpty()) {
            throw new IllegalArgumentException("Produtos and Pizzas lists cannot both be empty");
        }
    }
}
