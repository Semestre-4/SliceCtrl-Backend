package com.mensal.sliceCtrl.DTO;


import com.mensal.sliceCtrl.entity.Ingredientes;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class SaboresDTO extends AbstractEntityDTO {
    private String nomeSabor;
    private String descricao;
    private List<IngredientesDTO> ingredientes;
}
