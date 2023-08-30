package com.mensal.sliceCtrl.DTO;

import com.mensal.sliceCtrl.entity.Pedidos;
import com.mensal.sliceCtrl.entity.Sabores;
import com.mensal.sliceCtrl.entity.enums.Categorias;
import com.mensal.sliceCtrl.entity.enums.Tamanho;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class PizzasDTO extends AbstractEntityDTO{

    @NotNull(message = "O tamanho da pizza não pode ser nulo")
    private Tamanho tamanho;

    @Positive(message = "O preço da pizza deve ser um valor positivo")
    private Double preco;

    private String descricao;

}
