package com.mensal.sliceCtrl.service;

import com.mensal.sliceCtrl.DTO.SaboresDTO;
import com.mensal.sliceCtrl.entity.Sabores;
import org.springframework.stereotype.Service;

@Service
public class SaboresService {

    private SaboresDTO toSaboresDTO (Sabores sabores){
        SaboresDTO saboresDTO = new  SaboresDTO();

        saboresDTO.setNomeSabor(sabores.getNomeSabor());
        saboresDTO.setDescricao(sabores.getDescricao());
        saboresDTO.setIngredientes(sabores.getIngredientes());

        saboresDTO.setCadastro(sabores.getCadastro());
        saboresDTO.setAtivo(sabores.isAtivo());
        saboresDTO.setEdicao(sabores.getEdicao());
        saboresDTO.setId(sabores.getId());

        return saboresDTO;
    }

    private Sabores toSabores (SaboresDTO saboresDTO){
        Sabores sabores = new  Sabores();

        sabores.setNomeSabor(saboresDTO.getNomeSabor());
        sabores.setDescricao(saboresDTO.getDescricao());
        sabores.setIngredientes(saboresDTO.getIngredientes());

        sabores.setCadastro(saboresDTO.getCadastro());
        sabores.setAtivo(saboresDTO.isAtivo());
        sabores.setEdicao(saboresDTO.getEdicao());
        sabores.setId(sabores.getId());
        return sabores;
    }


}
