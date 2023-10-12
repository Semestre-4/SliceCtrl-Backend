package com.mensal.slicectrl.controller;

import com.mensal.slicectrl.dto.IngredientesDTO;
import com.mensal.slicectrl.service.IngredienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Esta classe representa o controlador para lidar com operações relacionadas a ingredientes.
 */

@RestController
@RequestMapping("/api/ingredientes")
public class IngredienteController {

    @Autowired
    private IngredienteService ingredienteService;

    /**
     * Recupera uma lista de todos os ingredientes.
     *
     * @return Lista contendo todos os ingredientes.
     */
    @GetMapping("/all")
    public ResponseEntity<List<IngredientesDTO>> getAllIngredientes() {
        List<IngredientesDTO> ingredientesDTOS = ingredienteService.getAll();
        return ResponseEntity.ok(ingredientesDTOS);
    }

    /**
     * Recupera um ingrediente pelo seu nome.
     *
     * @param nomeIngrediente O nome do ingrediente a ser recuperado.
     * @return ResponseEntity contendo as informações do ingrediente, se encontrado, ou uma resposta de "não encontrado".
     */
    @GetMapping("/nome/{nomeIngrediente}")
    public ResponseEntity<IngredientesDTO> getIngredienteByNome(@PathVariable("nomeIngrediente") String nomeIngrediente) {
        IngredientesDTO ingredientesDTO = ingredienteService.getByNome(nomeIngrediente);
        if (ingredientesDTO != null) {
            return ResponseEntity.ok(ingredientesDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Recupera um ingrediente pelo seu ID.
     *
     * @param id O ID do ingrediente a ser recuperado.
     * @return ResponseEntity contendo as informações do ingrediente, se encontrado, ou uma resposta de "não encontrado".
     */
    @GetMapping("/{id}")
    public ResponseEntity<IngredientesDTO> getIngredienteById(@PathVariable("id") Long id) {
        try {
            IngredientesDTO ingredientesDTO = ingredienteService.getById(id);
            if (ingredientesDTO != null) {
                return ResponseEntity.ok(ingredientesDTO);
            } else {
                return ResponseEntity.notFound().build();
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Cria um novo ingrediente.
     *
     * @param ingredientesDTO Os dados do ingrediente a serem cadastrados.
     * @return ResponseEntity indicando o sucesso ou a falha do cadastro.
     */
    @PostMapping
    public ResponseEntity<String> cadastrarIngrediente(@RequestBody @Validated IngredientesDTO ingredientesDTO) {
        try {
            this.ingredienteService.cadastrar(ingredientesDTO);
            return ResponseEntity.ok().body(String.format("O cadastro de '%s' foi realizado com sucesso.",
                    ingredientesDTO.getNomeIngrediente()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ocorreu um erro durante o cadastro: " + e.getMessage());
        }
    }

    /**
     * Edita as informações de um ingrediente.
     *
     * @param id           O ID do ingrediente a ser editado.
     * @param ingredientesDTO Os dados atualizados do ingrediente.
     * @return ResponseEntity indicando o sucesso ou a falha da edição.
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> editarIngrediente(@PathVariable("id") final Long id,
                                                     @RequestBody @Validated IngredientesDTO ingredientesDTO) {
        try {
            this.ingredienteService.editar(ingredientesDTO, id);
            return ResponseEntity.ok().body(String.format("O cadastro de '%s' foi atualizado com sucesso.",
                    ingredientesDTO.getNomeIngrediente()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ocorreu um erro durante a atualização: " + e.getMessage());
        }
    }

    /**
     * Exclui um ingrediente pelo seu ID.
     *
     * @param id O ID do ingrediente a ser excluído.
     * @return ResponseEntity indicando o sucesso ou a falha da exclusão.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirIngrediente(@PathVariable("id") final Long id) {
        try {
            this.ingredienteService.delete(id);
            return ResponseEntity.ok().body("Ingrediente excluído com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ocorreu um erro: " + e.getMessage());
        }
    }
}
