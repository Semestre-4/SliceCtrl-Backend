package com.mensal.sliceCtrl.DTO;

import com.mensal.sliceCtrl.entity.enums.Tamanho;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PizzasDTO extends AbstractEntityDTO{

    @NotNull(message = "O tamanho da pizza não pode ser nulo")
    private Tamanho tamanho;

    @Positive(message = "O preço da pizza deve ser um valor positivo")
    private Double preco;

}
