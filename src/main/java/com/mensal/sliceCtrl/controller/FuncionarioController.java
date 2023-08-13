package com.mensal.sliceCtrl.controller;


import com.mensal.sliceCtrl.DTO.FuncionariosDTO;
import com.mensal.sliceCtrl.entity.Funcionarios;
import com.mensal.sliceCtrl.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/funcionario")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    @GetMapping("/id/{id}")
    public ResponseEntity<FuncionariosDTO> getFuncionarioById(@PathVariable("id") Long id) {
        FuncionariosDTO cliente = funcionarioService.findById(id);
        if (cliente != null) {
            return ResponseEntity.ok(cliente);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<FuncionariosDTO>> getFuncionariosByNome(@PathVariable("nome") String nome) {
        List<FuncionariosDTO> funcionariosDTOS = funcionarioService.findByNome(nome);
        if (!funcionariosDTOS.isEmpty()) {
            return ResponseEntity.ok(funcionariosDTOS);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<FuncionariosDTO> getFuncionarioByCPF(@PathVariable("cpf") String cpf) {
        FuncionariosDTO funcionariosDTO = funcionarioService.findByCPF(cpf);
        if (funcionariosDTO != null) {
            return ResponseEntity.ok(funcionariosDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<FuncionariosDTO>> getAllFuncionarios() {
        List<FuncionariosDTO> funcionariosDTOS = funcionarioService.findAll();
        return ResponseEntity.ok(funcionariosDTOS);
    }

    @PostMapping
    public ResponseEntity<?> postFuncionario(@RequestBody @Validated FuncionariosDTO funcionariosDTO) {
        try {
            Funcionarios funcionarios = funcionarioService.createFuncionario(funcionariosDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(funcionarios);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putFuncionario(@PathVariable Long id, @RequestBody @Validated FuncionariosDTO funcionariosDTO) {
        try {
            Funcionarios updatedFuncionarios = funcionarioService.updateFuncionario(id, funcionariosDTO);
            return ResponseEntity.ok(updatedFuncionarios);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFuncionario(@PathVariable Long id) {
        try {
            funcionarioService.deleteFuncionario(id);
            return ResponseEntity.ok().body("Funcionario excluido com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
