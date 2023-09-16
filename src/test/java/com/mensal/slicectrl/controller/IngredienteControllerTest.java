package com.mensal.slicectrl.controller;

import com.mensal.slicectrl.dto.IngredientesDTO;
import com.mensal.slicectrl.service.IngredienteService;
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

public class IngredienteControllerTest {

    @InjectMocks
    private IngredienteController controller;

    @Mock
    private IngredienteService service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetIngredientesById() {
        Long ingredienteID = 1L;
        IngredientesDTO ingredientesDTO = new IngredientesDTO();

        Mockito.when(service.getById(ingredienteID)).thenReturn(ingredientesDTO);

        ResponseEntity<IngredientesDTO> response = controller.getIngredienteById(ingredienteID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ingredientesDTO, response.getBody());

        verify(service, times(1)).getById(ingredienteID);
    }

    @Test
    public void testGetAllIngredientes(){

        List<IngredientesDTO> ingredientesDTOList = new ArrayList<>();

        ingredientesDTOList.add(new IngredientesDTO("Mussarela", 200));

        Mockito.when(service.getAll()).thenReturn(ingredientesDTOList);

        ResponseEntity<List<IngredientesDTO>> response = controller.getAllIngredientes();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ingredientesDTOList, response.getBody());

        verify(service, times(1)).getAll();
    }

    @Test
    public void testGetByNomeIngredientes(){
        String nome = "Mussarela";
        IngredientesDTO ingredientesDTO = new IngredientesDTO("Mussarela", 200);

        Mockito.when(service.getByNome(nome)).thenReturn(ingredientesDTO);

        ResponseEntity<IngredientesDTO> response = controller.getIngredienteByNome(nome);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ingredientesDTO, response.getBody());

        verify(service, times(1)).getByNome(nome);

    }



}
