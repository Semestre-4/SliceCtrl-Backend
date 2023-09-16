package com.mensal.slicectrl.controller;

import com.mensal.slicectrl.dto.ClientesDTO;
import com.mensal.slicectrl.dto.PizzasDTO;
import com.mensal.slicectrl.entity.enums.Tamanho;
import com.mensal.slicectrl.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Esta classe representa a controller para lidar com operações relacionadas a pizzas.
 */

@RestController
@RequestMapping("api/pizza")
public class PizzaController {

    @Autowired
    public PizzaService pizzaService;

    /**
     * Recupera uma pizza pelo seu ID.
     *
     * @param id O ID da pizza a ser recuperada.
     * @return ResponseEntity contendo as informações da pizza, se encontrada, ou uma resposta de erro.
     */
    @GetMapping("/id/{id}")
    public ResponseEntity<PizzasDTO> getPizzaById(@PathVariable("id") Long id) {
        PizzasDTO pizzasDTO = pizzaService.findById(id);
        if (pizzasDTO != null) {
            return ResponseEntity.ok(pizzasDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<PizzasDTO>> getAllPizzas() {
        List<PizzasDTO> pizzaDTOS = pizzaService.findAll();
        return ResponseEntity.ok(pizzaDTOS);
    }


    @GetMapping("/tamanho/{tamanhoName}")
    public ResponseEntity<List<PizzasDTO>> getPizzaByTamanho(@PathVariable String tamanhoName) {
        try {
            Tamanho tamanho = Tamanho.valueOf(tamanhoName);
            List<PizzasDTO> pizzasDTOS = pizzaService.findByTamanho(tamanho);
            return ResponseEntity.ok(pizzasDTOS);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Tamanho Invalido: " + tamanhoName);
        }
    }

    /**
     * Cria uma nova pizza.
     *
     * @param pizzaDTO Os dados da pizza a serem cadastrados.
     * @return ResponseEntity indicando o sucesso ou a falha do cadastro.
     */
    @PostMapping
    public ResponseEntity<String> cadastrarPizza(@RequestBody @Validated PizzasDTO pizzaDTO) {
        try {
            pizzaService.createPizza(pizzaDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("O cadastro da pizza foi realizado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ocorreu um erro durante o cadastro: " + e.getMessage());
        }
    }

    /**
     * Atualiza as informações de uma pizza.
     *
     * @param id       O ID da pizza a ser editada.
     * @param pizzaDTO Os dados atualizados da pizza.
     * @return ResponseEntity indicando o sucesso ou a falha da edição.
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> editarPizza(@PathVariable Long id, @RequestBody @Validated PizzasDTO pizzaDTO) {
        try {
            pizzaService.updatePizza(id, pizzaDTO);
            return ResponseEntity.ok("O cadastro da pizza foi atualizado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ocorreu um erro durante a atualização: " + e.getMessage());
        }
    }

    /**
     * Exclui uma pizza pelo seu ID.
     *
     * @param id O ID da pizza a ser excluída.
     * @return ResponseEntity indicando o sucesso ou a falha da exclusão.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirPizza(@PathVariable Long id) {
        try {
            pizzaService.deletePizza(id);
            return ResponseEntity.ok().body("Pizza excluída com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ocorreu um erro: " + e.getMessage());
        }
    }
}
