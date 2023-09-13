package com.mensal.slicectrl.controller;

import com.mensal.slicectrl.dto.FuncionariosDTO;
import com.mensal.slicectrl.entity.Funcionarios;
import com.mensal.slicectrl.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Esta classe representa a controller para lidar com operações relacionadas a funcionários.
 */

@RestController
@RequestMapping("api/funcionario")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    /**
     * Recupera um funcionário pelo seu ID.
     *
     * @param id O ID do funcionário a ser recuperado.
     * @return ResponseEntity contendo as informações do funcionário, se encontrado, ou uma resposta de "não encontrado".
     */
    @GetMapping("/id/{id}")
    public ResponseEntity<FuncionariosDTO> getFuncionarioById(@PathVariable("id") Long id) {
        FuncionariosDTO cliente = funcionarioService.findById(id);
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
    public ResponseEntity<List<FuncionariosDTO>> getFuncionariosByNome(@PathVariable("nome") String nome) {
        List<FuncionariosDTO> funcionariosDTOS = funcionarioService.findByNome(nome);
        if (!funcionariosDTOS.isEmpty()) {
            return ResponseEntity.ok(funcionariosDTOS);
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
    public ResponseEntity<FuncionariosDTO> getFuncionarioByCPF(@PathVariable("cpf") String cpf) {
        FuncionariosDTO funcionariosDTO = funcionarioService.findByCPF(cpf);
        if (funcionariosDTO != null) {
            return ResponseEntity.ok(funcionariosDTO);
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
    public ResponseEntity<List<FuncionariosDTO>> getAllFuncionarios() {
        List<FuncionariosDTO> funcionariosDTOS = funcionarioService.findAll();
        return ResponseEntity.ok(funcionariosDTOS);
    }

    /**
     * Cria um novo funcionário.
     *
     * @param funcionariosDTO Os dados do funcionário a serem cadastrados.
     * @return ResponseEntity indicando o sucesso ou a falha do cadastro.
     */
    @PostMapping
    public ResponseEntity<String> cadastrarFuncionario(@RequestBody @Validated FuncionariosDTO funcionariosDTO) {
        try {
            Funcionarios funcionarios = funcionarioService.createFuncionario(funcionariosDTO);
            return ResponseEntity.ok(String.format("O cadastro de '%s' foi realizado com sucesso.",
                    funcionariosDTO.getNome()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ocorreu um erro durante o cadastro: " + e.getMessage());
        }
    }

    /**
     * Edita as informações de um funcionário.
     *
     * @param id          O ID do funcionário a ser editado.
     * @param funcionariosDTO Os dados atualizados do funcionário.
     * @return ResponseEntity indicando o sucesso ou a falha da edição.
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> editarFuncionario(@PathVariable Long id,
                                                    @RequestBody @Validated FuncionariosDTO funcionariosDTO) {
        try {
            Funcionarios updatedFuncionarios = funcionarioService.updateFuncionario(id, funcionariosDTO);
            return ResponseEntity.ok(String.format("O cadastro de '%s' foi atualizado com sucesso.",
                    funcionariosDTO.getNome()));
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
            funcionarioService.deleteFuncionario(id);
            return ResponseEntity.ok().body("Funcionário excluído com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ocorreu um erro: " + e.getMessage());
        }
    }

}
