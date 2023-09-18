package com.mensal.slicectrl.controller;

import com.mensal.slicectrl.dto.EnderecosDTO;
import com.mensal.slicectrl.service.EnderecoService;
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

public class EnderecoControllerTest {

    @InjectMocks
    private EnderecoController controller;

    @Mock
    private EnderecoService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetByIdEndereco() {
        Long enderecoId = 1L;
        EnderecosDTO enderecosDTO = new EnderecosDTO();

        Mockito.when(service.getById(enderecoId)).thenReturn(enderecosDTO);

        ResponseEntity<EnderecosDTO> response = controller.getEnderecoById(enderecoId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(enderecosDTO, response.getBody());

        verify(service, times(1)).getById(enderecoId);
    }



}
