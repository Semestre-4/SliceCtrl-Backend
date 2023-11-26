package com.mensal.slicectrl.controller;

import com.mensal.slicectrl.dto.UsuarioDTO;
import com.mensal.slicectrl.dto.UsuarioFrontDTO;
import com.mensal.slicectrl.entity.Usuario;
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
    public ResponseEntity<UsuarioFrontDTO> getFuncionarioById(@PathVariable("id") Long id) {
        UsuarioFrontDTO cliente = usuarioService.findById(id);
        if (cliente != null) {
            return ResponseEntity.ok(cliente);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<UsuarioFrontDTO>> getFuncionariosByNome(@PathVariable("nome") String nome) {
        List<UsuarioFrontDTO> usuarioDTOS = usuarioService.findByNome(nome);
        if (!usuarioDTOS.isEmpty()) {
            return ResponseEntity.ok(usuarioDTOS);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/ativo/{ativo}")
    public ResponseEntity<List<UsuarioFrontDTO>> getFuncionariosByAtivo(@PathVariable("ativo") boolean ativo) {
        List<UsuarioFrontDTO> usuarioDTOS = usuarioService.findByAtivo(ativo);
        if (!usuarioDTOS.isEmpty()) {
            return ResponseEntity.ok(usuarioDTOS);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<UsuarioFrontDTO> getFuncionarioByCPF(@PathVariable("cpf") String cpf) {
        UsuarioFrontDTO usuarioDTO = usuarioService.findByCPF(cpf);
        if (usuarioDTO != null) {
            return ResponseEntity.ok(usuarioDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<UsuarioFrontDTO>> getAllFuncionarios() {
        List<UsuarioFrontDTO> usuarioDTOS = usuarioService.findAll();
        return ResponseEntity.ok(usuarioDTOS);
    }

    @PostMapping
    public ResponseEntity<String> cadastrarUsuario(@RequestBody @Validated Usuario usuario) {
        try {
            usuarioService.createUsuario(usuario);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ocorreu um erro durante o cadastro: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> editarUsuario(@PathVariable Long id,
                                                @RequestBody @Validated Usuario usuario) {
        try {
            usuarioService.updateUsuario(id, usuario);
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
