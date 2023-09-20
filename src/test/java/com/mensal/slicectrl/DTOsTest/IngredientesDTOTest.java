package com.mensal.slicectrl.DTOsTest;


import com.mensal.slicectrl.dto.IngredientesDTO;
import com.mensal.slicectrl.dto.ProdutosDTO;
import com.mensal.slicectrl.entity.enums.Categoria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class IngredientesDTOTest {


    private IngredientesDTO ingredientesDTO;

    @BeforeEach
    void setUp() {
        ingredientesDTO = new IngredientesDTO();
    }

    @Test
    void testValidEnderecoDTO() {

        ingredientesDTO.setNomeIngrediente("Mussarela");
        ingredientesDTO.setQtdeIngrediente(200);

        assertDoesNotThrow(() -> validateEnderecoDTO(ingredientesDTO));
    }

    void validateEnderecoDTO(IngredientesDTO dto) {
        if (dto.getNomeIngrediente() == null || dto.getNomeIngrediente().length() < 2 || dto.getNomeIngrediente().length() > 50) {
            throw new IllegalArgumentException("Invalid name ingrediente");
        }

        if (dto.getQtdeIngrediente() < 0) {
            throw new IllegalArgumentException("Invalid QtdeIngrediente");
        }

    }

}
