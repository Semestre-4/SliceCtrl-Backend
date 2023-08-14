package com.mensal.sliceCtrl.service;


import com.mensal.sliceCtrl.DTO.PizzasDTO;
import com.mensal.sliceCtrl.entity.Pizzas;
import com.mensal.sliceCtrl.entity.enums.Tamanho;
import com.mensal.sliceCtrl.repository.PizzaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PizzaService {

    private final PizzaRepository pizzaRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PizzaService(PizzaRepository pizzaRepository, ModelMapper modelMapper) {
        this.pizzaRepository = pizzaRepository;
        this.modelMapper = modelMapper;
    }


    public PizzasDTO findById(Long id) {
        try {
            Pizzas pizzaEncontrado = pizzaRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Pizza não encontrada com o ID: " + id));
            return toPizzaDTO(pizzaEncontrado);
        } catch (EntityNotFoundException ex) {
            throw new RuntimeException("Ocorreu um erro ao tentar recuperar a pizza.", ex);
        }
    }

    public PizzasDTO findByNome(String nomeProduto) {
        try {
            Pizzas pizzaEncontrado = pizzaRepository.findByNome(nomeProduto);
            return toPizzaDTO(pizzaEncontrado);
        } catch (EntityNotFoundException ex) {
            throw new RuntimeException("Ocorreu um erro ao tentar recuperar a pizza.", ex);
        }
    }

    public PizzasDTO findByNomeSabor(String nomeSabor) {
        try {
            Pizzas pizzaEncontrado = pizzaRepository.findByNomeSabor(nomeSabor);
            return toPizzaDTO(pizzaEncontrado);
        } catch (EntityNotFoundException ex) {
            throw new RuntimeException("Ocorreu um erro ao tentar recuperar a pizza.", ex);
        }
    }

    public List<PizzasDTO> findByTamanho(Tamanho tamanho) {
        return pizzaRepository.findByTamanho(tamanho).stream().map(this::toPizzaDTO).toList();
    }

    public List<PizzasDTO> findAll() {
        return pizzaRepository.findAll().stream().map(this::toPizzaDTO).toList();
    }

    public List<PizzasDTO> findByDisponivel() {
        return pizzaRepository.findByDisponivelTrue().stream().map(this::toPizzaDTO).toList();
    }

    public Pizzas createPizza(PizzasDTO pizzaDTO) {
        Pizzas pizza = toPizza(pizzaDTO);
        return pizzaRepository.save(pizza);
    }

    public Pizzas updatePizza(Long id, PizzasDTO pizzaDTO) {
        Pizzas existingPizza = pizzaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pizza com ID = " + id + " nao encontrada"));

        Pizzas pizza = toPizza(pizzaDTO);
        return pizzaRepository.save(pizza);
    }

    public void deletePizza(Long id) {
        Pizzas pizzaToDelete = pizzaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pizza com ID = : " + id + "nao encontrada"));
        pizzaRepository.delete(pizzaToDelete);
    }

    public PizzasDTO toPizzaDTO(Pizzas pizza) {
        return modelMapper.map(pizza, PizzasDTO.class);
    }

    public Pizzas toPizza(PizzasDTO pizzaDTO) {
        return modelMapper.map(pizzaDTO, Pizzas.class);
    }

}
