package com.mensal.sliceCtrl.controller;

import com.mensal.sliceCtrl.DTO.SaboresDTO;
import com.mensal.sliceCtrl.service.SaboresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sabores")
public class SaboresController {

    @Autowired
    private SaboresService saboresService;

    @GetMapping("/all")
    private List<SaboresDTO> getAll(){
        return this.saboresService.getAll();
    }

    @GetMapping("/nome={nomeSabor}")
    private SaboresDTO getByNome(@PathVariable("nomeSabor") String nomeSabor){
        return this.saboresService.getByNome(nomeSabor);
    }

    @GetMapping("/{id}")
    private SaboresDTO getById(@PathVariable("id") Long id){
        return this.saboresService.getById(id);
    }

    @PostMapping
    private ResponseEntity<String> cadastrar(@RequestBody SaboresDTO saboresDTO){
        try{
            this.saboresService.cadastrar(saboresDTO);
            return  ResponseEntity.ok().body("Cadastrado com sucesso!");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping
    private ResponseEntity<String> editar(@RequestBody SaboresDTO saboresDTO){
        try {
            this.saboresService.editar(saboresDTO);
            return ResponseEntity.ok().body("Editado com sucesso!");
            }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/id={id}")
    private ResponseEntity<String> deletar(@PathVariable("id") Long id){
        try{
            this.saboresService.deletar(id);
            return ResponseEntity.ok().body("Deletado com sucesso!");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }


}
