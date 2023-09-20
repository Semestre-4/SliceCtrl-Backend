package com.mensal.slicectrl.ServiceTest;

import com.mensal.slicectrl.dto.PizzasDTO;
import com.mensal.slicectrl.entity.Enderecos;
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

import java.util.Collections;
import java.util.List;

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

    Pizzas pizzas = new Pizzas();
    PizzasDTO pizzaDTO = new PizzasDTO(Tamanho.P, 92.8);

    @BeforeEach
    void setup() {
        pizzas.setId(1L);
        pizzas.setTamanho(Tamanho.P);
        pizzas.setPreco(92.8);
        when(pizzaRepository.findByTamanho(Tamanho.P)).thenReturn(Collections.singletonList(pizzas));
        when(modelMapper.map(pizzas, PizzasDTO.class)).thenReturn(pizzaDTO);
        when(pizzaService.toPizza(pizzaDTO)).thenReturn(pizzas);
        when(pizzaService.toPizzaDTO(pizzas)).thenReturn(pizzaDTO);
        when(pizzaRepository.save(pizzas)).thenReturn(pizzas);
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
