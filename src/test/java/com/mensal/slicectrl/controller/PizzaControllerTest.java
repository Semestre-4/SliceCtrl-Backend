package com.mensal.slicectrl.controller;

import com.mensal.slicectrl.dto.PizzasDTO;
import com.mensal.slicectrl.entity.enums.Tamanho;
import com.mensal.slicectrl.service.PizzaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@SpringBootTest
class PizzaControllerTest {

    @InjectMocks
    private PizzaController pizzaController;

    @Mock
    private PizzaService pizzaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPizzaById() {
        Long pizzaId = 1L;
        PizzasDTO pizzaDTO = new PizzasDTO(); // Create a sample PizzasDTO

        when(pizzaService.findById(pizzaId)).thenReturn(pizzaDTO);

        ResponseEntity<PizzasDTO> response = pizzaController.getPizzaById(pizzaId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pizzaDTO, response.getBody());

        verify(pizzaService, times(1)).findById(pizzaId);
    }

    @Test
    void testGetPizzaByIdNotFound() {
        Long pizzaId = 1L;

        // Simulate the service returning null, indicating that the pizza was not found
        when(pizzaService.findById(pizzaId)).thenReturn(null);

        ResponseEntity<PizzasDTO> response = pizzaController.getPizzaById(pizzaId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()); // Expecting NOT_FOUND here
        assertNull(response.getBody()); // Ensure the response body is null for a not found pizza

        verify(pizzaService, times(1)).findById(pizzaId);
    }


    @Test
    void testGetAllPizzas() {
        List<PizzasDTO> pizzaDTOList = new ArrayList<>(); // Create a list of sample PizzasDTO

        when(pizzaService.findAll()).thenReturn(pizzaDTOList);

        ResponseEntity<List<PizzasDTO>> response = pizzaController.getAllPizzas();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pizzaDTOList, response.getBody());

        verify(pizzaService, times(1)).findAll();
    }

    @Test
    void testGetPizzaByTamanho() {
        String tamanhoName = "M";
        Tamanho tamanho = Tamanho.M;
        List<PizzasDTO> pizzaDTOList = new ArrayList<>(); // Create a list of sample PizzasDTO

        // Mock the service method to return the sample list
        when(pizzaService.findByTamanho(tamanho)).thenReturn(pizzaDTOList);

        ResponseEntity<List<PizzasDTO>> response = pizzaController.getPizzaByTamanho(tamanhoName);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pizzaDTOList, response.getBody());

        verify(pizzaService, times(1)).findByTamanho(tamanho);
    }


    @Test
    void testCadastrarPizzaSuccess() {
        PizzasDTO pizzaDTO = new PizzasDTO(); // Create a sample PizzasDTO

        ResponseEntity<String> response = pizzaController.cadastrarPizza(pizzaDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("O cadastro da pizza foi realizado com sucesso.", response.getBody());

        verify(pizzaService, times(1)).createPizza(pizzaDTO);
    }

    @Test
    void testCadastrarPizzaError() {
        PizzasDTO pizzaDTO = new PizzasDTO(); // Create a sample PizzasDTO

        // Simulate an exception being thrown when creating the pizza
        when(pizzaService.createPizza(pizzaDTO)).thenThrow(new RuntimeException("Something went wrong"));

        ResponseEntity<String> response = pizzaController.cadastrarPizza(pizzaDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Ocorreu um erro durante o cadastro: Something went wrong", response.getBody());

        verify(pizzaService, times(1)).createPizza(pizzaDTO);
    }


    @Test
    void testPutPizzaSuccess() {
        Long pizzaId = 1L;
        PizzasDTO pizzaDTO = new PizzasDTO(); // Create a sample PizzasDTO

        ResponseEntity<String> response = pizzaController.editarPizza(pizzaId, pizzaDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("O cadastro da pizza foi atualizado com sucesso.", response.getBody());

        verify(pizzaService, times(1)).updatePizza(pizzaId, pizzaDTO);
    }

    @Test
    void testPutPizzaError() {
        Long pizzaId = 1L;
        PizzasDTO pizzaDTO = new PizzasDTO(); // Create a sample PizzasDTO

        // Simulate an exception being thrown when updating the pizza
        when(pizzaService.updatePizza(pizzaId, pizzaDTO)).thenThrow(new RuntimeException("Update failed"));

        ResponseEntity<String> response = pizzaController.editarPizza(pizzaId, pizzaDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Ocorreu um erro durante a atualização: Update failed", response.getBody());

        verify(pizzaService, times(1)).updatePizza(pizzaId, pizzaDTO);
    }


    @Test
    void testExcluirPizza() {
        Long pizzaId = 1L;

        ResponseEntity<String> response = pizzaController.excluirPizza(pizzaId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Pizza excluída com sucesso!", response.getBody());

        verify(pizzaService, times(1)).deletePizza(pizzaId);
    }


}
