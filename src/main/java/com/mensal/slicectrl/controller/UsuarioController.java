package com.mensal.slicectrl.controller;

import com.mensal.slicectrl.dto.UsuarioDTO;
import com.mensal.slicectrl.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Esta classe representa a controller para lidar com operações relacionadas a funcionários.
 */

@RestController
@RequestMapping("/api/usuario")
@CrossOrigin("http://localhost:4200")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/id/{id}")
    public ResponseEntity<UsuarioDTO> getFuncionarioById(@PathVariable("id") Long id) {
        UsuarioDTO cliente = usuarioService.findById(id);
        if (cliente != null) {
            return ResponseEntity.ok(cliente);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<UsuarioDTO>> getFuncionariosByNome(@PathVariable("nome") String nome) {
        List<UsuarioDTO> usuarioDTOS = usuarioService.findByNome(nome);
        if (!usuarioDTOS.isEmpty()) {
            return ResponseEntity.ok(usuarioDTOS);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/ativo/{ativo}")
    public ResponseEntity<List<UsuarioDTO>> getFuncionariosByAtivo(@PathVariable("ativo") boolean ativo) {
        List<UsuarioDTO> usuarioDTOS = usuarioService.findByAtivo(ativo);
        if (!usuarioDTOS.isEmpty()) {
            return ResponseEntity.ok(usuarioDTOS);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<UsuarioDTO> getFuncionarioByCPF(@PathVariable("cpf") String cpf) {
        UsuarioDTO usuarioDTO = usuarioService.findByCPF(cpf);
        if (usuarioDTO != null) {
            return ResponseEntity.ok(usuarioDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<UsuarioDTO>> getAllFuncionarios() {
        List<UsuarioDTO> usuarioDTOS = usuarioService.findAll();
        return ResponseEntity.ok(usuarioDTOS);
    }

    @PostMapping
    public ResponseEntity<String> cadastrarFuncionario(@RequestBody @Validated UsuarioDTO usuarioDTO) {
        try {
            usuarioService.createFuncionario(usuarioDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ocorreu um erro durante o cadastro: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> editarFuncionario(@PathVariable Long id,
                                                    @RequestBody @Validated UsuarioDTO usuarioDTO) {
        try {
            usuarioService.updateFuncionario(id, usuarioDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ocorreu um erro durante o cadastro: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirFuncionario(@PathVariable Long id) {
        try {
            usuarioService.deleteFuncionario(id);
            return ResponseEntity.ok().body("Funcionário excluído com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ocorreu um erro: " + e.getMessage());
        }
    }

}
