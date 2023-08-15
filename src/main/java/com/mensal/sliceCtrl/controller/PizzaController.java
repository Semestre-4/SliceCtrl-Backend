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

@RestController
@RequestMapping("api/pizza")
public class PizzaController {

    @Autowired
    public PizzaService pizzaService;

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getPizzaById(@PathVariable("id") Long id) {
        try {
            PizzasDTO pizzaDTO = pizzaService.findById(id);
            return ResponseEntity.ok(pizzaDTO);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e);
        }

    }

    @GetMapping("/sabor/{nomeSabor}")
    public ResponseEntity<PizzasDTO> getPizzaByNomeSabor(@PathVariable("nomeSabor") String nomeSabor) {
        PizzasDTO pizzaDTOS = pizzaService.findByNomeSabor(nomeSabor);
        if (pizzaDTOS != null) {
            return ResponseEntity.ok(pizzaDTOS);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/tamanho/{tamanhoName}")
    public List<PizzasDTO> getPizzaByTamanho(@PathVariable String tamanhoName) {
        try {
            Tamanho tamanho = Tamanho.valueOf(tamanhoName);
            return pizzaService.findByTamanho(tamanho);
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
    private List<PizzasDTO> getByAvailable(){
        return this.pizzaService.findByDisponivel();
    }

    @PostMapping
    public ResponseEntity<?> postPizza(@RequestBody @Validated PizzasDTO pizzaDTO) {
        try{
            Pizzas createdPizza = pizzaService.createPizza(pizzaDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPizza);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putPizza(@PathVariable Long id, @RequestBody @Validated PizzasDTO pizzaDTO) {
        try {
            Pizzas updatedPizza = pizzaService.updatePizza(id, pizzaDTO);
            return ResponseEntity.ok(updatedPizza);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePizza(@PathVariable Long id) {
        try {
            pizzaService.deletePizza(id);
            return ResponseEntity.ok().body("Pizza excluida com sucesso!");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
