package com.mensal.slicectrl.controller;

import com.mensal.slicectrl.dto.ClientesDTO;
import com.mensal.slicectrl.dto.EnderecosDTO;
import com.mensal.slicectrl.service.ClienteService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ClienteControllerTest {
    @Mock
    private ClienteController clienteController;

    @Autowired
    private ClienteService clienteService;

    ClientesDTO clientesDTO = new ClientesDTO();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        ClientesDTO clientesDTO = createSampleClient();
        when(clienteController.getClienteById(1L)).thenReturn(ResponseEntity.ok(clientesDTO));
    }

    private ClientesDTO createSampleClient() {
        clientesDTO.setId(1L);
        clientesDTO.setNome("Exemplo de Nome");
        clientesDTO.setCpf("189.382.829-72");
        clientesDTO.setEmail("email@example.com");
        clientesDTO.setTelefone("829-29828");

        List<EnderecosDTO> enderecosList = new ArrayList<>();
        EnderecosDTO endereco1 = new EnderecosDTO();


        endereco1.setRua("Rua Exemplo");
        endereco1.setNumero(123);
        endereco1.setComplemento("Complemento Exemplo");
        endereco1.setBairro("Bairro Exemplo");
        endereco1.setCidade("Cidade Exemplo");
        endereco1.setEstado("SP");
        endereco1.setPais("País Exemplo");
        endereco1.setCep("1234567");


        enderecosList.add(endereco1);
        clientesDTO.setEnderecos(enderecosList);
        return clientesDTO;
    }


    @Test
    public void testGetClienteById() {
        Long clientId = 1L;
        ClientesDTO expectedClient = createSampleClient();
        ResponseEntity<ClientesDTO> response = clienteController.getClienteById(clientId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedClient, response.getBody());
    }


}
