package com.mensal.slicectrl.dto;

import com.mensal.slicectrl.entity.enums.Tamanho;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class PizzasDTO extends AbstractEntityDTO{

    @NotNull(message = "O tamanho da pizza não pode ser nulo")
    private Tamanho tamanho;

    @Positive(message = "O preço da pizza deve ser um valor positivo")
    private Double preco;

    public PizzasDTO(Tamanho tamanho, Double preco) {
        this.tamanho = tamanho;
        this.preco = preco;
    }
}
