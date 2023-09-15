package com.mensal.slicectrl.service;


import com.mensal.slicectrl.dto.PizzasDTO;
import com.mensal.slicectrl.entity.Pizzas;
import com.mensal.slicectrl.entity.enums.Tamanho;
import com.mensal.slicectrl.repository.PizzaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
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
            throw new IllegalArgumentException("Ocorreu um erro ao tentar recuperar a pizza.", ex);
        }
    }

    public List<PizzasDTO> findByTamanho(Tamanho tamanho) {
        return pizzaRepository.findByTamanho(tamanho).stream().map(this::toPizzaDTO).toList();
    }

    public List<PizzasDTO> findAll() {
        return pizzaRepository.findAll().stream().map(this::toPizzaDTO).toList();
    }

    @Transactional
    public Pizzas createPizza(PizzasDTO pizzaDTO) {
        Pizzas pizza = toPizza(pizzaDTO);
        return pizzaRepository.save(pizza);
    }


    @Transactional
    public Pizzas updatePizza(Long id, PizzasDTO pizzaDTO) {
        if (!pizzaRepository.existsById(id)) {
            throw new EntityNotFoundException("Pizza com ID = " + id + " não encontrada");
        }
        return pizzaRepository.save(toPizza(pizzaDTO));
    }


    @Transactional
    public void deletePizza(Long id) {
        Pizzas pizzaToDelete = pizzaRepository.findById(id).orElse(null);
        if (pizzaToDelete != null) {
            if (!pizzaToDelete.getPedidos().isEmpty()) {
                throw new IllegalArgumentException("Não é possível excluir a pizza devido à relação com pedidos existentes.");
            } else {
                pizzaRepository.delete(pizzaToDelete);
            }
        }

    }

    public PizzasDTO toPizzaDTO(Pizzas pizza) {
        return modelMapper.map(pizza, PizzasDTO.class);
    }

    public Pizzas toPizza(PizzasDTO pizzaDTO) {
        return modelMapper.map(pizzaDTO, Pizzas.class);
    }

}
