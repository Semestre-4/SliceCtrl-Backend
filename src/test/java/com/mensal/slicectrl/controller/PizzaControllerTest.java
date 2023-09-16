package com.mensal.slicectrl.controller;

import com.mensal.slicectrl.dto.PizzasDTO;
import com.mensal.slicectrl.entity.enums.Tamanho;
import com.mensal.slicectrl.service.PizzaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PizzaControllerTest {

    @InjectMocks
    private PizzaController pizzaController;

    @Mock
    private PizzaService pizzaService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetPizzaById() {
        Long pizzaId = 1L;
        PizzasDTO pizzaDTO = new PizzasDTO(); // Create a sample PizzasDTO

        when(pizzaService.findById(pizzaId)).thenReturn(pizzaDTO);

        ResponseEntity<PizzasDTO> response = pizzaController.getPizzaById(pizzaId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pizzaDTO, response.getBody());

        verify(pizzaService, times(1)).findById(pizzaId);
    }

    @Test
    public void testGetAllPizzas() {
        List<PizzasDTO> pizzaDTOList = new ArrayList<>(); // Create a list of sample PizzasDTO

        when(pizzaService.findAll()).thenReturn(pizzaDTOList);

        ResponseEntity<List<PizzasDTO>> response = pizzaController.getAllPizzas();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pizzaDTOList, response.getBody());

        verify(pizzaService, times(1)).findAll();
    }

    @Test
    public void testGetPizzaByTamanho() {
        String tamanhoName = "M";
        Tamanho tamanho = Tamanho.M;
        List<PizzasDTO> pizzaDTOList = new ArrayList<>(); // Create a list of sample PizzasDTO

        when(pizzaService.findByTamanho(tamanho)).thenReturn(pizzaDTOList);

        ResponseEntity<List<PizzasDTO>> response = pizzaController.getPizzaByTamanho(tamanhoName);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pizzaDTOList, response.getBody());

        verify(pizzaService, times(1)).findByTamanho(tamanho);
    }

    @Test
    public void testCadastrarPizza() {
        PizzasDTO pizzaDTO = new PizzasDTO(); // Create a sample PizzasDTO

        ResponseEntity<String> response = pizzaController.cadastrarPizza(pizzaDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("O cadastro da pizza foi realizado com sucesso.", response.getBody());

        verify(pizzaService, times(1)).createPizza(pizzaDTO);
    }

    @Test
    public void testEditarPizza() {
        Long pizzaId = 1L;
        PizzasDTO pizzaDTO = new PizzasDTO(); // Create a sample PizzasDTO

        ResponseEntity<String> response = pizzaController.editarPizza(pizzaId, pizzaDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("O cadastro da pizza foi atualizado com sucesso.", response.getBody());

        verify(pizzaService, times(1)).updatePizza(pizzaId, pizzaDTO);
    }

    @Test
    public void testExcluirPizza() {
        Long pizzaId = 1L;

        ResponseEntity<String> response = pizzaController.excluirPizza(pizzaId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Pizza excluída com sucesso!", response.getBody());

        verify(pizzaService, times(1)).deletePizza(pizzaId);
    }
}
