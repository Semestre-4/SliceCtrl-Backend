package com.mensal.slicectrl.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mensal.slicectrl.dto.ClientesDTO;
import com.mensal.slicectrl.dto.EnderecosDTO;
import com.mensal.slicectrl.entity.Clientes;
import com.mensal.slicectrl.entity.Enderecos;
import com.mensal.slicectrl.service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClienteController.class)
public class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;

    @Autowired
    private ObjectMapper objectMapper;

    private ClientesDTO clientesDTO;
    private Clientes clientes;

    @BeforeEach
    public void setUp() {
        clientesDTO = createSampleClientDTO();
        clientes = createSampleClient();
    }

    @Test
    public void testGetClienteById() throws Exception {
        when(clienteService.findById(1L)).thenReturn(clientesDTO);

        mockMvc.perform(get("/api/cliente/id/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void testGetClientesByNome() throws Exception {
        List<ClientesDTO> sampleClientes = new ArrayList<>();
        sampleClientes.add(clientesDTO);
        when(clienteService.findByNome("Exemplo de Nome")).thenReturn(sampleClientes);

        mockMvc.perform(get("/api/cliente/nome/Exemplo de Nome"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testGetClientesByCPF() throws Exception {
        when(clienteService.findByCPF("189.382.829-72")).thenReturn(clientesDTO);
        mockMvc.perform(get("/api/cliente/189.382.829-72"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cpf").value("189.382.829-72"));
    }

    @Test
    public void testGetAllClientes() throws Exception {
        List<ClientesDTO> sampleClientes = new ArrayList<>();
        sampleClientes.add(clientesDTO);

        when(clienteService.findAll()).thenReturn(sampleClientes);

        mockMvc.perform(get("/api/cliente/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testCadastrarCliente() throws Exception {
        mockMvc.perform(post("/api/cliente")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(clientesDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("O cadastro de 'Exemplo de Nome' foi realizado com sucesso."));
    }

    private Clientes createSampleClient() {
        Clientes sampleClientes = new Clientes();
        sampleClientes.setId(1L);
        sampleClientes.setNome("Exemplo de Nome");
        sampleClientes.setCpf("189.382.829-72");
        sampleClientes.setEmail("email@example.com");
        sampleClientes.setTelefone("829-29828");

        List<Enderecos> enderecosList = new ArrayList<>();
        Enderecos endereco1 = new Enderecos();

        endereco1.setRua("Rua Exemplo");
        endereco1.setNumero(123);
        endereco1.setComplemento("Complemento Exemplo");
        endereco1.setBairro("Bairro Exemplo");
        endereco1.setCidade("Cidade Exemplo");
        endereco1.setEstado("SP");
        endereco1.setPais("País Exemplo");
        endereco1.setCep("1234567");

        enderecosList.add(endereco1);
        sampleClientes.setEnderecos(enderecosList);
        return sampleClientes;
    }

    private ClientesDTO createSampleClientDTO() {
        ClientesDTO sampleClientesDTO = new ClientesDTO();
        sampleClientesDTO.setId(1L);
        sampleClientesDTO.setNome("Exemplo de Nome");
        sampleClientesDTO.setCpf("189.382.829-72");
        sampleClientesDTO.setEmail("email@example.com");
        sampleClientesDTO.setTelefone("829-29828");

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
        sampleClientesDTO.setEnderecos(enderecosList);
        return sampleClientesDTO;
    }
}
