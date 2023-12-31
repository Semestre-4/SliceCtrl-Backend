package com.mensal.slicectrl.controller;

import com.mensal.slicectrl.dto.SaboresDTO;
import com.mensal.slicectrl.service.SaboresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Esta classe representa a controller para lidar com operações relacionadas a sabores.
 */

@RestController
@RequestMapping("/api/sabores")
@CrossOrigin("http://localhost:4200")
public class SaboresController {

    @Autowired
    private SaboresService saboresService;

    @GetMapping("/all")
    public ResponseEntity<List<SaboresDTO>> getAllSabores() {
        List<SaboresDTO> saboresDTOS = saboresService.getAll();
        return ResponseEntity.ok(saboresDTOS);
    }

    @GetMapping("/nome/{nomeSabor}")
    public ResponseEntity<SaboresDTO> getByNome(@PathVariable("nomeSabor") String nomeSabor){
        SaboresDTO saboresDTO = saboresService.getByNome(nomeSabor);
        if (saboresDTO != null) {
            return ResponseEntity.ok(saboresDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<SaboresDTO> getById(@PathVariable("id") Long id){
        SaboresDTO saboresDTO = saboresService.getById(id);
        if (saboresDTO != null) {
            return ResponseEntity.ok(saboresDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/ativo/{ativo}")
    public ResponseEntity<List<SaboresDTO>> getAllByAtivo(@PathVariable boolean ativo){
        try {
            List<SaboresDTO> saboresDTOS = saboresService.findByAtivo(ativo);
            return ResponseEntity.ok(saboresDTOS);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Ouve algum erro.");
        }
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('FUNCIONARIO_CHEF','ADMIN')")
    public ResponseEntity<String> cadastrarSabor(@RequestBody @Validated SaboresDTO saboresDTO) {
        try {
            this.saboresService.cadastrar(saboresDTO);
            return ResponseEntity.ok().body(String.format("O cadastro de '%s' foi realizado com sucesso.",
                    saboresDTO.getNomeSabor()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ocorreu um erro durante o cadastro: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('FUNCIONARIO_CHEF','ADMIN')")
    public ResponseEntity<String> editarSabor(@PathVariable Long id,
                                               @RequestBody @Validated SaboresDTO saboresDTO) {
        try {
            this.saboresService.editar(id,saboresDTO);
            return ResponseEntity.ok().body(String.format("O cadastro de '%s' foi atualizado com sucesso.",
                    saboresDTO.getNomeSabor()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ocorreu um erro durante a atualização: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('FUNCIONARIO_CHEF','ADMIN')")
    public ResponseEntity<String> excluirSabor(@PathVariable("id") Long id){
        try{
            this.saboresService.deletar(id);
            return ResponseEntity.ok().body("Sabor excluido com sucesso!");
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Ocorreu um erro: " + e.getMessage());
        }

    }


}
