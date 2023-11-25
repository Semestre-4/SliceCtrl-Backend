package com.mensal.slicectrl.ServiceTest;

import com.mensal.slicectrl.dto.PizzasDTO;
import com.mensal.slicectrl.entity.Pizzas;
import com.mensal.slicectrl.entity.enums.Tamanho;
import com.mensal.slicectrl.repository.PizzaRepository;
import com.mensal.slicectrl.service.PizzaService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class PizzaServiceTest {

    @Mock
    private PizzaRepository pizzaRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PizzaService pizzaService;

    List<Pizzas> pizzasDTOS = new ArrayList<>();
    Pizzas pizzas = new Pizzas();
    PizzasDTO pizzaDTO = new PizzasDTO(Tamanho.P, 92.8);

    @BeforeEach
    void setup() {
        pizzas.setId(1L);
        pizzas.setTamanho(Tamanho.P);
        pizzas.setPreco(92.8);
        pizzasDTOS.add(pizzas);
        when(pizzaRepository.findByTamanho(Tamanho.P)).thenReturn(Collections.singletonList(pizzas));
        when(pizzaRepository.findById(1L)).thenReturn(Optional.of(pizzas));
        when(pizzaRepository.findById(2L)).thenReturn(Optional.empty());
        when(pizzaRepository.findAll()).thenReturn(pizzasDTOS);
        when(modelMapper.map(pizzas, PizzasDTO.class)).thenReturn(pizzaDTO);
        when(pizzaService.toPizza(pizzaDTO)).thenReturn(pizzas);
        when(pizzaService.toPizzaDTO(pizzas)).thenReturn(pizzaDTO);
        when(pizzaRepository.save(pizzas)).thenReturn(pizzas);
    }

    @Test
    void testFindById_ValidId() {
        PizzasDTO result = pizzaService.findById(1L);
        assertNotNull(result);
    }

    @Test
    void testFindById_InvalidId() {
        assertThrows(IllegalArgumentException.class, () -> pizzaService.findById(2L));
    }

    @Test
    void testGetAll() {
        List<PizzasDTO> result = pizzaService.findAll();
        assertEquals(1, result.size());
    }

    @Test
    void testFindByTamanho() {
        Tamanho tamanho = Tamanho.P;
        List<PizzasDTO> result = pizzaService.findByTamanho(tamanho);
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(pizzaRepository, times(1)).findByTamanho(tamanho);
    }

    @Test
    void testCadastrarPizzaService() {
        Pizzas resposta = pizzaService.createPizza(pizzaDTO);
        assertNotNull(resposta);
        assertEquals(resposta, pizzas);
    }
    @Test
    void testUpdatePizzaWhenPizzaNotFound() {
        Long id = 1L;
        when(pizzaRepository.existsById(id)).thenReturn(false);

        PizzasDTO updatedPizzaDTO = new PizzasDTO(Tamanho.G, 100.0);

        assertThrows(EntityNotFoundException.class, () -> {
            pizzaService.updatePizza(id, updatedPizzaDTO);
        });
    }


}
