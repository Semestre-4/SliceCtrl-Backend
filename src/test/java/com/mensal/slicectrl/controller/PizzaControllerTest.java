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
    void testObterPizzaPorId() {
        Long pizzaId = 1L;
        PizzasDTO pizzaDTO = new PizzasDTO(); // Cria um PizzasDTO de exemplo

        when(pizzaService.findById(pizzaId)).thenReturn(pizzaDTO);

        ResponseEntity<PizzasDTO> response = pizzaController.getPizzaById(pizzaId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pizzaDTO, response.getBody());

        verify(pizzaService, times(1)).findById(pizzaId);
    }

    @Test
    void testObterPizzaPorIdNaoEncontrada() {
        Long pizzaId = 1L;

        // Simula o serviço retornando null, indicando que a pizza não foi encontrada
        when(pizzaService.findById(pizzaId)).thenReturn(null);

        ResponseEntity<PizzasDTO> response = pizzaController.getPizzaById(pizzaId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()); // Esperando NOT_FOUND aqui
        assertNull(response.getBody()); // Certifique-se de que o corpo da resposta seja nulo para uma pizza não encontrada

        verify(pizzaService, times(1)).findById(pizzaId);
    }

    @Test
    void testObterTodasAsPizzas() {
        List<PizzasDTO> pizzaDTOList = new ArrayList<>(); // Cria uma lista de PizzasDTO de exemplo

        when(pizzaService.findAll()).thenReturn(pizzaDTOList);

        ResponseEntity<List<PizzasDTO>> response = pizzaController.getAllPizzas();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pizzaDTOList, response.getBody());

        verify(pizzaService, times(1)).findAll();
    }

    @Test
    void testObterPizzaPorTamanho() {
        String tamanhoName = "M";
        Tamanho tamanho = Tamanho.M;
        List<PizzasDTO> pizzaDTOList = new ArrayList<>(); // Cria uma lista de PizzasDTO de exemplo

        // Simula o método do serviço para retornar a lista de exemplo
        when(pizzaService.findByTamanho(tamanho)).thenReturn(pizzaDTOList);

        ResponseEntity<List<PizzasDTO>> response = pizzaController.getPizzaByTamanho(tamanhoName);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pizzaDTOList, response.getBody());

        verify(pizzaService, times(1)).findByTamanho(tamanho);
    }

    @Test
    void testCadastrarPizzaSucesso() {
        PizzasDTO pizzaDTO = new PizzasDTO(); // Cria um PizzasDTO de exemplo

        ResponseEntity<String> response = pizzaController.cadastrarPizza(pizzaDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("O cadastro da pizza foi realizado com sucesso.", response.getBody());

        verify(pizzaService, times(1)).createPizza(pizzaDTO);
    }

    @Test
    void testCadastrarPizzaErro() {
        PizzasDTO pizzaDTO = new PizzasDTO(); // Cria um PizzasDTO de exemplo

        // Simula uma exceção sendo lançada ao criar a pizza
        when(pizzaService.createPizza(pizzaDTO)).thenThrow(new RuntimeException("Algo deu errado"));

        ResponseEntity<String> response = pizzaController.cadastrarPizza(pizzaDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Ocorreu um erro durante o cadastro: Algo deu errado", response.getBody());

        verify(pizzaService, times(1)).createPizza(pizzaDTO);
    }

    @Test
    void testAtualizarPizzaSucesso() {
        Long pizzaId = 1L;
        PizzasDTO pizzaDTO = new PizzasDTO(); // Cria um PizzasDTO de exemplo

        ResponseEntity<String> response = pizzaController.editarPizza(pizzaId, pizzaDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("O cadastro da pizza foi atualizado com sucesso.", response.getBody());

        verify(pizzaService, times(1)).updatePizza(pizzaId, pizzaDTO);
    }

    @Test
    void testAtualizarPizzaErro() {
        Long pizzaId = 1L;
        PizzasDTO pizzaDTO = new PizzasDTO(); // Cria um PizzasDTO de exemplo

        // Simula uma exceção sendo lançada ao atualizar a pizza
        when(pizzaService.updatePizza(pizzaId, pizzaDTO)).thenThrow(new RuntimeException("Falha na atualização"));

        ResponseEntity<String> response = pizzaController.editarPizza(pizzaId, pizzaDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Ocorreu um erro durante a atualização: Falha na atualização", response.getBody());

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
