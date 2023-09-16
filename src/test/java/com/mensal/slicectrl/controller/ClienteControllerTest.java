package com.mensal.slicectrl.controller;

import com.mensal.slicectrl.dto.ClientesDTO;
import com.mensal.slicectrl.service.ClienteService;
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
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ClienteControllerTest {

    @InjectMocks
    private ClienteController clienteController;

    @Mock
    private ClienteService clienteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetClienteById() {
        Long clienteId = 1L;
        ClientesDTO clienteDTO = new ClientesDTO(); // Criar um ClienteDTO de exemplo

        when(clienteService.findById(clienteId)).thenReturn(clienteDTO);

        ResponseEntity<ClientesDTO> response = clienteController.getClienteById(clienteId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(clienteDTO, response.getBody());

        verify(clienteService, times(1)).findById(clienteId);
    }

    @Test
    void testGetClienteByIdNotFound() {
        Long clienteId = 1L;

        when(clienteService.findById(clienteId)).thenReturn(null);

        ResponseEntity<ClientesDTO> response = clienteController.getClienteById(clienteId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

        verify(clienteService, times(1)).findById(clienteId);
    }

    @Test
    void testGetClientesByNome() {
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
    void testGetClientesByNomeNotFound() {
        String nome = "NonExistentName";

        List<ClientesDTO> emptyList = new ArrayList<>(); // Uma lista vazia para simular nenhum cliente encontrado

        when(clienteService.findByNome(nome)).thenReturn(emptyList);

        ResponseEntity<List<ClientesDTO>> response = clienteController.getClientesByNome(nome);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

        verify(clienteService, times(1)).findByNome(nome);
    }

    @Test
    void testGetClientesByCPF() {
        String cpf = "12345678901";
        ClientesDTO clienteDTO = new ClientesDTO(); // Criar um ClienteDTO de exemplo

        when(clienteService.findByCPF(cpf)).thenReturn(clienteDTO);

        ResponseEntity<ClientesDTO> response = clienteController.getClientesByCPF(cpf);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(clienteDTO, response.getBody());

        verify(clienteService, times(1)).findByCPF(cpf);
    }

    @Test
    void testGetClientesByCPFNotFound() {
        String cpf = "NonExistentCPF";

        when(clienteService.findByCPF(cpf)).thenReturn(null);

        ResponseEntity<ClientesDTO> response = clienteController.getClientesByCPF(cpf);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

        verify(clienteService, times(1)).findByCPF(cpf);
    }

    @Test
    void testGetAllClientes() {
        List<ClientesDTO> clienteDTOList = new ArrayList<>(); // Criar uma lista de ClienteDTO de exemplo

        when(clienteService.findAll()).thenReturn(clienteDTOList);

        ResponseEntity<List<ClientesDTO>> response = clienteController.getAllClientes();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(clienteDTOList, response.getBody());

        verify(clienteService, times(1)).findAll();
    }

    @Test
    void testCadastrarClienteSuccess() {
        ClientesDTO clienteDTO = new ClientesDTO(); // Criar um ClienteDTO de exemplo
        clienteDTO.setNome("Sample Name");

        ResponseEntity<String> expectedResponse = ResponseEntity.ok("O cadastro de 'Sample Name' foi realizado com sucesso.");

        try {
            when(clienteService.createCliente(clienteDTO)).thenReturn(null); // Simular retorno nulo quando bem-sucedido
            ResponseEntity<String> response = clienteController.cadastrarCliente(clienteDTO);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(expectedResponse.getBody(), response.getBody());

            verify(clienteService, times(1)).createCliente(clienteDTO);
        } catch (Exception e) {
            // Lidar com exceções inesperadas
            fail("Exceção inesperada: " + e.getMessage());
        }
    }

    @Test
    void testCadastrarClienteError() {
        ClientesDTO clienteDTO = new ClientesDTO(); // Criar um ClienteDTO de exemplo
        clienteDTO.setNome("Sample Name");

        when(clienteService.createCliente(clienteDTO)).thenThrow(RuntimeException.class); // Simular uma exceção de tempo de execução

        ResponseEntity<String> response = clienteController.cadastrarCliente(clienteDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertFalse(Objects.requireNonNull(response.getBody()).contains("Exception"));

        verify(clienteService, times(1)).createCliente(clienteDTO);
    }

    @Test
    void testPutClienteSuccess() {
        Long clienteId = 1L;
        ClientesDTO clienteDTO = new ClientesDTO(); // Criar um ClienteDTO de exemplo
        clienteDTO.setNome("Sample Name");

        ResponseEntity<String> expectedResponse = ResponseEntity.ok("O cadastro de 'Sample Name' foi atualizado com sucesso.");

        // Simular o método updateCliente para retornar nulo quando bem-sucedido
        when(clienteService.updateCliente(clienteId, clienteDTO)).thenReturn(null);

        ResponseEntity<String> response = clienteController.putCliente(clienteId, clienteDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse.getBody(), response.getBody());

        verify(clienteService, times(1)).updateCliente(clienteId, clienteDTO);
    }

    @Test
    void testPutClienteError() {
        Long clienteId = 1L;
        ClientesDTO clienteDTO = new ClientesDTO(); // Criar um ClienteDTO de exemplo
        clienteDTO.setNome("Sample Name");

        when(clienteService.updateCliente(clienteId, clienteDTO)).thenThrow(RuntimeException.class);

        ResponseEntity<String> response = clienteController.putCliente(clienteId, clienteDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertFalse(Objects.requireNonNull(response.getBody()).contains("Exception"));

        verify(clienteService, times(1)).updateCliente(clienteId, clienteDTO);
    }

    @Test
    void testExcluirCliente() {
        Long clienteId = 1L;

        ResponseEntity<String> response = clienteController.excluirCliente(clienteId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Cliente excluído com sucesso!", response.getBody());

        verify(clienteService, times(1)).deleteCliente(clienteId);
    }
}
