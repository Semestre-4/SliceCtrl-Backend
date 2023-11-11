package com.mensal.slicectrl.controller;

import com.mensal.slicectrl.dto.UsuarioDTO;
import com.mensal.slicectrl.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Esta classe representa a controller para lidar com operações relacionadas a funcionários.
 */

@RestController
@RequestMapping("/api/funcionario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Recupera um funcionário pelo seu ID.
     *
     * @param id O ID do funcionário a ser recuperado.
     * @return ResponseEntity contendo as informações do funcionário, se encontrado, ou uma resposta de "não encontrado".
     */
    @GetMapping("/id/{id}")
    public ResponseEntity<UsuarioDTO> getFuncionarioById(@PathVariable("id") Long id) {
        UsuarioDTO cliente = usuarioService.findById(id);
        if (cliente != null) {
            return ResponseEntity.ok(cliente);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Recupera uma lista de funcionários pelo nome.
     *
     * @param nome O nome dos funcionários a serem recuperados.
     * @return ResponseEntity contendo a lista de funcionários, se encontrados.
     */
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

    /**
     * Recupera um funcionário pelo CPF.
     *
     * @param cpf O CPF do funcionário a ser recuperado.
     * @return ResponseEntity contendo as informações do funcionário, se encontrado, ou uma resposta de "não encontrado".
     */
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<UsuarioDTO> getFuncionarioByCPF(@PathVariable("cpf") String cpf) {
        UsuarioDTO usuarioDTO = usuarioService.findByCPF(cpf);
        if (usuarioDTO != null) {
            return ResponseEntity.ok(usuarioDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Recupera uma lista de todos os funcionários.
     *
     * @return ResponseEntity contendo a lista de todos os funcionários.
     */
    @GetMapping("/all")
    public ResponseEntity<List<UsuarioDTO>> getAllFuncionarios() {
        List<UsuarioDTO> usuarioDTOS = usuarioService.findAll();
        return ResponseEntity.ok(usuarioDTOS);
    }

    /**
     * Cria um novo funcionário.
     *
     * @param usuarioDTO Os dados do funcionário a serem cadastrados.
     * @return ResponseEntity indicando o sucesso ou a falha do cadastro.
     */
    @PostMapping
    public ResponseEntity<String> cadastrarFuncionario(@RequestBody @Validated UsuarioDTO usuarioDTO) {
        try {
            usuarioService.createFuncionario(usuarioDTO);
            return ResponseEntity.ok(String.format("O cadastro de '%s' foi realizado com sucesso.",
                    usuarioDTO.getNome()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ocorreu um erro durante o cadastro: " + e.getMessage());
        }
    }

    /**
     * Edita as informações de um funcionário.
     *
     * @param id          O ID do funcionário a ser editado.
     * @param usuarioDTO Os dados atualizados do funcionário.
     * @return ResponseEntity indicando o sucesso ou a falha da edição.
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> editarFuncionario(@PathVariable Long id,
                                                    @RequestBody @Validated UsuarioDTO usuarioDTO) {
        try {
            usuarioService.updateFuncionario(id, usuarioDTO);
            return ResponseEntity.ok(String.format("O cadastro de '%s' foi atualizado com sucesso.",
                    usuarioDTO.getNome()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ocorreu um erro durante o cadastro: " + e.getMessage());
        }
    }

    /**
     * Exclui um funcionário pelo seu ID.
     *
     * @param id O ID do funcionário a ser excluído.
     * @return ResponseEntity indicando o sucesso ou a falha da exclusão.
     */
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
