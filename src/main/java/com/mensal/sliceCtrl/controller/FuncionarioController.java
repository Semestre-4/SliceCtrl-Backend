package com.mensal.sliceCtrl.controller;


import com.mensal.sliceCtrl.DTO.FuncionarioDTO;
import com.mensal.sliceCtrl.entity.Cliente;
import com.mensal.sliceCtrl.entity.Funcionario;
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
    public ResponseEntity<FuncionarioDTO> getFuncionarioById(@PathVariable("id") Long id) {
        FuncionarioDTO cliente = funcionarioService.findById(id);
        if (cliente != null) {
            return ResponseEntity.ok(cliente);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<FuncionarioDTO>> getFuncionariosByNome(@PathVariable("nome") String nome) {
        List<FuncionarioDTO> funcionarioDTOS = funcionarioService.findByNome(nome);
        if (!funcionarioDTOS.isEmpty()) {
            return ResponseEntity.ok(funcionarioDTOS);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<FuncionarioDTO> getFuncionarioByCPF(@PathVariable("cpf") String cpf) {
        FuncionarioDTO funcionarioDTO = funcionarioService.findByCPF(cpf);
        if (funcionarioDTO != null) {
            return ResponseEntity.ok(funcionarioDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<FuncionarioDTO>> getAllFuncionarios() {
        List<FuncionarioDTO> funcionarioDTOS = funcionarioService.findAll();
        return ResponseEntity.ok(funcionarioDTOS);
    }

    @PostMapping
    public ResponseEntity<?> postFuncionario(@RequestBody @Validated FuncionarioDTO funcionarioDTO) {
        try {
            Funcionario funcionario = funcionarioService.createFuncionario(funcionarioDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(funcionario);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putFuncionario(@PathVariable Long id, @RequestBody @Validated FuncionarioDTO funcionarioDTO) {
        try {
            Funcionario updatedFuncionario = funcionarioService.updateFuncionario(id, funcionarioDTO);
            return ResponseEntity.ok(updatedFuncionario);
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
