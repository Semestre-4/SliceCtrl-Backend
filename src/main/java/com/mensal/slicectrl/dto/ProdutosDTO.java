package com.mensal.slicectrl.dto;

import com.mensal.slicectrl.entity.enums.Categoria;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProdutosDTO extends AbstractEntityDTO {

    @NotBlank(message = "O nome do produto não pode estar em branco")
    private String nomeProduto;

    @NotNull(message = "A categoria do produto não pode ser nula")
    private Categoria categoria;

    @Min(value = 0, message = "A quantidade em estoque deve ser no mínimo 0")
    private Integer qtdeEstoque;

    @NotNull(message = "O campo disponivel no produto não pode ser nula")
    private boolean disponivel;

    @Positive(message = "O preço do produto deve ser um valor positivo")
    @NotNull(message = "O preço do produto não pode ser nulo")
    private Double preco;

}
