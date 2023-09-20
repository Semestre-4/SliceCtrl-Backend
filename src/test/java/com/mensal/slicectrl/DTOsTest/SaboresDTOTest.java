package com.mensal.slicectrl.DTOsTest;


import com.mensal.slicectrl.dto.IngredientesDTO;
import com.mensal.slicectrl.dto.ProdutosDTO;
import com.mensal.slicectrl.dto.SaboresDTO;
import com.mensal.slicectrl.entity.enums.Categoria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class SaboresDTOTest {


    private SaboresDTO saboresDTO;

    @BeforeEach
    void setUp() {
        saboresDTO = new SaboresDTO();
    }

    @Test
    void testValidEnderecoDTO() {
        List<IngredientesDTO> ingredientesDTOList = new ArrayList<>();
        ingredientesDTOList.add(new IngredientesDTO("Mussarela", 200));

        saboresDTO.setNomeSabor("Mussarela");
        saboresDTO.setIngredientesDTOS(ingredientesDTOList);

        assertDoesNotThrow(() -> validateEnderecoDTO(saboresDTO));
    }

    void validateEnderecoDTO(SaboresDTO dto) {
        if (dto.getNomeSabor() == null || dto.getNomeSabor().length() < 2 || dto.getNomeSabor().length() > 50) {
            throw new IllegalArgumentException("Invalid name sabor");
        }

        if (dto.getIngredientesDTOS() == null || dto.getIngredientesDTOS().isEmpty()) {
            throw new IllegalArgumentException("Invalid ingredientes");
        }


    }

}
