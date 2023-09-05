package com.mensal.sliceCtrl.controller;

import com.mensal.sliceCtrl.DTO.PizzasDTO;
import com.mensal.sliceCtrl.entity.Pizzas;
import com.mensal.sliceCtrl.entity.enums.Tamanho;
import com.mensal.sliceCtrl.service.PizzaService;
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
        try {
            PizzasDTO pizzaDTO = pizzaService.findById(id);
            return ResponseEntity.ok(pizzaDTO);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao recuperar a pizza.");
        }
    }

    @GetMapping("/tamanho/{tamanhoName}")
    public ResponseEntity<List<PizzasDTO>> getPizzaByTamanho(@PathVariable String tamanhoName) {
        try {
            Tamanho tamanho = Tamanho.valueOf(tamanhoName);
            List<PizzasDTO> pizzasDTOS = pizzaService.findByTamanho(tamanho);
            return ResponseEntity.ok(pizzasDTOS);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Tamanho Invalido: " + tamanhoName);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<PizzasDTO>> getAllPizzas() {
        List<PizzasDTO> pizzaDTOS = pizzaService.findAll();
        return ResponseEntity.ok(pizzaDTOS);
    }


    @GetMapping("/disponivel")
    private ResponseEntity<List<PizzasDTO>> getByAvailable(){
        List<PizzasDTO> pizzasDTOS = pizzaService.findByDisponivel();
        return ResponseEntity.ok(pizzasDTOS);
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
            Pizzas createdPizza = pizzaService.createPizza(pizzaDTO);
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
            Pizzas updatedPizza = pizzaService.updatePizza(id, pizzaDTO);
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
