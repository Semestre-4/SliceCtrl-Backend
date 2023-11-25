package com.mensal.slicectrl.controller;

import com.mensal.slicectrl.dto.PizzasDTO;
import com.mensal.slicectrl.entity.enums.Tamanho;
import com.mensal.slicectrl.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Esta classe representa a controller para lidar com operações relacionadas a pizzas.
 */

@RestController
@RequestMapping("/api/pizza")
@CrossOrigin("http://localhost:4200")
public class PizzaController {

    @Autowired
    public PizzaService pizzaService;

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

    @GetMapping("/ativo/{ativo}")
    public ResponseEntity<List<PizzasDTO>> getAllByAtivo(@PathVariable boolean ativo){
        try {
            List<PizzasDTO> pizzasDTOS = pizzaService.findByAtivo(ativo);
            return ResponseEntity.ok(pizzasDTOS);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Ouve algum erro.");
        }
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

    @PreAuthorize("hasAnyRole('ADMIN', 'FUNCIONARIO')")
    @PostMapping
    public ResponseEntity<String> cadastrarPizza(@RequestBody @Validated PizzasDTO pizzaDTO) {
        try {
            pizzaService.createPizza(pizzaDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("O cadastro da pizza foi realizado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ocorreu um erro durante o cadastro: " + e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'FUNCIONARIO')")
    @PutMapping("/{id}")
    public ResponseEntity<String> editarPizza(@PathVariable Long id, @RequestBody @Validated PizzasDTO pizzaDTO) {
        try {
            pizzaService.updatePizza(id, pizzaDTO);
            return ResponseEntity.ok("O cadastro da pizza foi atualizado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ocorreu um erro durante a atualização: " + e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'FUNCIONARIO')")
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
