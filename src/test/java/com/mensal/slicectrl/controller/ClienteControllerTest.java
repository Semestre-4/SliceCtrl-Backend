package com.mensal.slicectrl.controller;

import com.mensal.slicectrl.dto.ClientesDTO;
import com.mensal.slicectrl.service.ClienteService;
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
import static org.mockito.Mockito.when;

public class ClienteControllerTest {
    @InjectMocks
    private ClienteController clienteController;

    @Mock
    private ClienteService clienteService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetClienteById() {

        Long clientId = 1L;
        ClientesDTO mockClient = new ClientesDTO();
        when(clienteService.findById(clientId)).thenReturn(mockClient);

        ResponseEntity<ClientesDTO> response = clienteController.getClienteById(clientId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockClient, response.getBody());
    }


}
