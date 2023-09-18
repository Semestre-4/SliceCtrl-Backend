package com.mensal.slicectrl.controller;

import com.mensal.slicectrl.dto.ProdutosDTO;
import com.mensal.slicectrl.dto.SaboresDTO;
import com.mensal.slicectrl.service.ProdutoService;
import com.mensal.slicectrl.service.SaboresService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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



}
