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
import static org.mockito.Mockito.*;

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
        Long clienteId = 1L;
        ClientesDTO clienteDTO = new ClientesDTO(); // Create a sample ClientesDTO

        when(clienteService.findById(clienteId)).thenReturn(clienteDTO);

        ResponseEntity<ClientesDTO> response = clienteController.getClienteById(clienteId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(clienteDTO, response.getBody());

        verify(clienteService, times(1)).findById(clienteId);
    }

    @Test
    public void testGetClientesByNome() {
        String nome = "John";

        List<ClientesDTO> clienteDTOList = new ArrayList<>();
        ClientesDTO johnDoe = new ClientesDTO();
        johnDoe.setNome("John");
        clienteDTOList.add(johnDoe);

        when(clienteService.findByNome(nome)).thenReturn(clienteDTOList);

        ResponseEntity<List<ClientesDTO>> response = clienteController.getClientesByNome(nome);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(clienteDTOList, response.getBody());

        verify(clienteService, times(1)).findByNome(nome);
    }


    @Test
    public void testGetClientesByCPF() {
        String cpf = "12345678901";
        ClientesDTO clienteDTO = new ClientesDTO(); // Create a sample ClientesDTO

        when(clienteService.findByCPF(cpf)).thenReturn(clienteDTO);

        ResponseEntity<ClientesDTO> response = clienteController.getClientesByCPF(cpf);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(clienteDTO, response.getBody());

        verify(clienteService, times(1)).findByCPF(cpf);
    }

    @Test
    public void testGetAllClientes() {
        List<ClientesDTO> clienteDTOList = new ArrayList<>(); // Create a list of sample ClientesDTO

        when(clienteService.findAll()).thenReturn(clienteDTOList);

        ResponseEntity<List<ClientesDTO>> response = clienteController.getAllClientes();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(clienteDTOList, response.getBody());

        verify(clienteService, times(1)).findAll();
    }

    @Test
    public void testCadastrarCliente() {
        ClientesDTO clienteDTO = new ClientesDTO();
        clienteDTO.setNome("Sample Name");

        ResponseEntity<String> response = clienteController.cadastrarCliente(clienteDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("O cadastro de 'Sample Name' foi realizado com sucesso.", response.getBody());

        verify(clienteService, times(1)).createCliente(clienteDTO);
    }

    @Test
    public void testPutCliente() {
        Long clienteId = 1L;
        ClientesDTO clienteDTO = new ClientesDTO();
        clienteDTO.setNome("Edited Name");

        ResponseEntity<String> response = clienteController.putCliente(clienteId, clienteDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("O cadastro de 'Edited Name' foi atualizado com sucesso.", response.getBody());

        verify(clienteService, times(1)).updateCliente(clienteId, clienteDTO);
    }

    @Test
    public void testExcluirCliente() {
        Long clienteId = 1L;

        ResponseEntity<String> response = clienteController.excluirCliente(clienteId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Cliente excluído com sucesso!", response.getBody());

        verify(clienteService, times(1)).deleteCliente(clienteId);
    }
}
