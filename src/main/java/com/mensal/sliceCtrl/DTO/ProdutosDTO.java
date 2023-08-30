package com.mensal.sliceCtrl.DTO;

import com.mensal.sliceCtrl.entity.enums.Categoria;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
public class ProdutosDTO extends AbstractEntityDTO {

    @NotBlank(message = "O nome do produto não pode estar em branco")
    private String nomeProduto;

    @NotNull(message = "A categoria do produto não pode ser nula")
    private Categoria categoria;

    @Min(value = 0, message = "A quantidade em estoque deve ser no mínimo 0")
    private Integer qtdeEstoque;

    @Positive(message = "O preço do produto deve ser um valor positivo")
    private Double preco;

}
