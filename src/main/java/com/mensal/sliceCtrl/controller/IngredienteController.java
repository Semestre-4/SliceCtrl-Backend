package com.mensal.sliceCtrl.controller;

import com.mensal.sliceCtrl.DTO.IngredientesDTO;
import com.mensal.sliceCtrl.service.IngredienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ingredientes")
public class IngredienteController {

    @Autowired
    private IngredienteService ingredienteService;


    @GetMapping("/all")
    private List<IngredientesDTO> getAll(){ return this.ingredienteService.getAll(); }

    @GetMapping("/nome")
    private IngredientesDTO getByNome(@RequestParam("nome") String nome){
        return this.ingredienteService.getByNome(nome);
    }


    @PostMapping
    private ResponseEntity<String> cadastrar(@RequestBody IngredientesDTO ingredientesDTO){
        try{
        this.ingredienteService.cadastrar(ingredientesDTO);
        return ResponseEntity.ok().body("Cadastrado com sucesso");
    }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
