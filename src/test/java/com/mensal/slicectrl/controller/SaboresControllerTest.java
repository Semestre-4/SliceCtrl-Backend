package com.mensal.slicectrl.controller;

import com.mensal.slicectrl.dto.IngredientesDTO;
import com.mensal.slicectrl.dto.SaboresDTO;
import com.mensal.slicectrl.service.SaboresService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class SaboresControllerTest {

    @InjectMocks
    private SaboresController controller;

    @Mock
    private SaboresService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetByIdSabores() {
        Long saboresId = 1L;
        SaboresDTO saboresDTO = new SaboresDTO();

        Mockito.when(service.getById(saboresId)).thenReturn(saboresDTO);

        ResponseEntity<SaboresDTO> response = controller.getById(saboresId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(saboresDTO, response.getBody());

        verify(service, times(1)).getById(saboresId);
    }

    @Test
    void testGetByIdSaboresNotFound() {

        Mockito.when(service.getById(null)).thenReturn(null);

        ResponseEntity<SaboresDTO> response = controller.getById(null);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(service, times(1)).getById(null);
    }

    @Test
    void testGetAllSabores(){

        List<SaboresDTO> saboresDTOList = new ArrayList<>();

        saboresDTOList.add(new SaboresDTO());

        Mockito.when(service.getAll()).thenReturn(saboresDTOList);

        ResponseEntity<List<SaboresDTO>> response = controller.getAllSabores();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(saboresDTOList, response.getBody());

        verify(service, times(1)).getAll();
    }

    @Test
    void testGetByNomeSabores() {
        String nome = "Teste";
        List<IngredientesDTO> ingredientesDTOList= new ArrayList<>();
        SaboresDTO saboresDTO = new SaboresDTO("Teste", ingredientesDTOList);

        Mockito.when(service.getByNome(nome)).thenReturn(saboresDTO);

        ResponseEntity<SaboresDTO> response = controller.getByNome(nome);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(saboresDTO, response.getBody());

        verify(service, times(1)).getByNome(nome);

    }

    @Test
    void testGetByNomeSaboresNotFound() {
        String nome = "Teste";

        Mockito.when(service.getByNome(nome)).thenReturn(null);

        ResponseEntity<SaboresDTO> response = controller.getByNome(nome);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(service, times(1)).getByNome(nome);

    }

    @Test
    void testCadastrarSabores(){
        List<IngredientesDTO> ingredientesDTOList= new ArrayList<>();
        SaboresDTO saboresDTO = new SaboresDTO("Teste", ingredientesDTOList);

        ResponseEntity<String> response = controller.cadastrarSabor(saboresDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("O cadastro de 'Teste' foi realizado com sucesso.", response.getBody());

        verify(service, times(1)).cadastrar(saboresDTO);

    }


    @Test
    void testCadastrarSaboresError(){


        ResponseEntity<String> response = controller.cadastrarSabor(null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        verify(service, times(1)).cadastrar(null);

    }

    @Test
    void testEditSabores(){
        List<IngredientesDTO> ingredientesDTOList= new ArrayList<>();
        SaboresDTO saboresDTO = new SaboresDTO("Teste", ingredientesDTOList);

        ResponseEntity<String> response = controller.editarSabor(1L, saboresDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("O cadastro de 'Teste' foi atualizado com sucesso.", response.getBody());

        verify(service, times(1)).editar(1L, saboresDTO);

    }

    @Test
    void testEditSaboresError(){

        ResponseEntity<String> response = controller.editarSabor(1L, null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        verify(service, times(1)).editar(1L, null);

    }

    @Test
    void testDeleteSabores(){
        Long saborId = 1L;

        ResponseEntity<String> response = controller.excluirSabor(saborId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Sabor excluido com sucesso!", response.getBody());

        verify(service, times(1)).deletar(saborId);

    }


    }
