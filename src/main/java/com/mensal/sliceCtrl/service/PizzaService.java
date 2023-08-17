package com.mensal.sliceCtrl.service;


import com.mensal.sliceCtrl.DTO.PizzasDTO;
import com.mensal.sliceCtrl.DTO.SaboresDTO;
import com.mensal.sliceCtrl.entity.Pizzas;
import com.mensal.sliceCtrl.entity.Sabores;
import com.mensal.sliceCtrl.entity.enums.Tamanho;
import com.mensal.sliceCtrl.repository.PizzaRepository;
import com.mensal.sliceCtrl.repository.SaboresRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PizzaService {

    private final PizzaRepository pizzaRepository;
    private final SaboresRepository saboresRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PizzaService(PizzaRepository pizzaRepository,SaboresRepository saboresRepository, ModelMapper modelMapper) {
        this.pizzaRepository = pizzaRepository;
        this.saboresRepository = saboresRepository;
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

    @Transactional
    public Pizzas createPizza(PizzasDTO pizzaDTO) {
        List<Sabores> sabores = new ArrayList<>();
        for (SaboresDTO saboresDTO : pizzaDTO.getSabor()) {
            Sabores saboresEx = saboresRepository.findById(saboresDTO.getId())
                    .orElseThrow(() ->
                            new EntityNotFoundException("Sabor com ID = " + saboresDTO.getId() + " não encontrado"));
            sabores.add(saboresEx);
            if (saboresEx.getValorAdicional() != 0) {
               pizzaDTO.setPreco(pizzaDTO.getPreco()+saboresEx.getValorAdicional());
            }
        }

        int numberOfSabores = sabores.size();
        int minSabores = 1;
        int maxSabores;

        if (pizzaDTO.getTamanho() == Tamanho.P) {
            maxSabores = 1;
        } else if (pizzaDTO.getTamanho() == Tamanho.M) {
            maxSabores = 2;
        } else if (pizzaDTO.getTamanho() == Tamanho.G) {
            maxSabores = 3;
        } else if (pizzaDTO.getTamanho() == Tamanho.XG) {
            maxSabores = 4;
        } else {
            throw new IllegalArgumentException("Tamanho de pizza inválido: " + pizzaDTO.getTamanho());
        }

        if (numberOfSabores < minSabores || numberOfSabores > maxSabores) {
            throw new IllegalArgumentException("Quantidade de sabores inválida para o tamanho da pizza " + pizzaDTO.getTamanho());
        }

        Pizzas pizza = toPizza(pizzaDTO, sabores);
        return pizzaRepository.save(pizza);
    }


    @Transactional
    public Pizzas updatePizza(Long id, PizzasDTO pizzaDTO) {
        Pizzas existingPizza = pizzaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pizza com ID = " + id + " não encontrada"));

        List<Sabores> sabores = new ArrayList<>();
        for (SaboresDTO saboresDTO : pizzaDTO.getSabor()) {
            Sabores saboresEx = saboresRepository.findById(saboresDTO.getId())
                    .orElseThrow(() ->
                            new EntityNotFoundException("Sabor com ID = " + saboresDTO.getId() + " não encontrada"));
            sabores.add(saboresEx);
        }

        int numberOfSabores = sabores.size();
        int minSabores = 1;
        int maxSabores;

        if (pizzaDTO.getTamanho() == Tamanho.P) {
            maxSabores = 1;
        } else if (pizzaDTO.getTamanho() == Tamanho.M) {
            maxSabores = 2;
        } else if (pizzaDTO.getTamanho() == Tamanho.G) {
            maxSabores = 3;
        } else if (pizzaDTO.getTamanho() == Tamanho.XG) {
            maxSabores = 4;
        } else {
            throw new IllegalArgumentException("Tamanho de pizza inválido: " + pizzaDTO.getTamanho());
        }

        if (numberOfSabores < minSabores || numberOfSabores > maxSabores) {
            throw new IllegalArgumentException("Quantidade de sabores inválida para o tamanho da pizza " + pizzaDTO.getTamanho());
        }

        return pizzaRepository.save(toPizza(pizzaDTO,sabores));
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

    public Pizzas toPizza(PizzasDTO pizzaDTO,List<Sabores> sabores) {
        Pizzas pizzas = modelMapper.map(pizzaDTO, Pizzas.class);
        pizzas.setSabor(sabores);
        return pizzas;
    }

}
