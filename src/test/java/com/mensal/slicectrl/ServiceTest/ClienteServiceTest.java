package com.mensal.slicectrl.ServiceTest;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.mensal.slicectrl.dto.ClientesDTO;
import com.mensal.slicectrl.dto.EnderecosDTO;
import com.mensal.slicectrl.entity.Clientes;
import com.mensal.slicectrl.entity.Enderecos;
import com.mensal.slicectrl.repository.ClienteRepository;
import com.mensal.slicectrl.repository.EnderecoRepository;
import com.mensal.slicectrl.service.ClienteService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@SpringBootTest
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private EnderecoRepository enderecoRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ClienteService clienteService;

    private List<Clientes> clientesList = new ArrayList<>();
    Clientes sampleCliente = new Clientes();
    Clientes sampleCliente1 = new Clientes();
    Clientes sampleCliente2 = new Clientes();
    List<EnderecosDTO> enderecosDTO = new ArrayList<>();


    @BeforeEach
    void setUp() {
        String validCPF = "0202938920";

        sampleCliente.setId(1L);
        sampleCliente.setNome("John");
        sampleCliente.setCpf(validCPF);
        sampleCliente.setTelefone("92020808320");
        sampleCliente.setEmail("sdjnkjds@kdjee.dd");

        List<Enderecos> enderecos = new ArrayList<>();
        Enderecos endereco = new Enderecos();
        enderecos.add(endereco);
        sampleCliente.setEnderecos(enderecos);

        sampleCliente1.setId(1L);
        sampleCliente1.setNome("John");
        sampleCliente1.setCpf("0202938920");


        sampleCliente2.setId(2L);
        sampleCliente2.setNome("Alice");
        sampleCliente2.setCpf("723798932323");

        clientesList.add(sampleCliente1);
        clientesList.add(sampleCliente2);

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(sampleCliente));
        when(clienteRepository.findById(2L)).thenReturn(Optional.empty());
        when(clienteRepository.findAll()).thenReturn(clientesList);
        when(clienteRepository.findByNome("John")).thenReturn(List.of(sampleCliente));
        when(clienteRepository.findByCpf(validCPF)).thenReturn(sampleCliente);
        when(modelMapper.map(sampleCliente, ClientesDTO.class)).thenReturn(new ClientesDTO());

        when(modelMapper.map(sampleCliente1, ClientesDTO.class)).thenReturn(new ClientesDTO());
        when(modelMapper.map(sampleCliente2, ClientesDTO.class)).thenReturn(new ClientesDTO());
        when(modelMapper.map(sampleCliente, ClientesDTO.class)).thenReturn(new ClientesDTO());
    }


    @Test
    void testFindById_ValidId() {
        ClientesDTO result = clienteService.findById(1L);
        assertNotNull(result);
    }

    @Test
    void testFindById_InvalidId() {
        assertThrows(IllegalArgumentException.class, () -> clienteService.findById(2L));
    }

    @Test
    void testGetAll() {
        List<ClientesDTO> result = clienteService.findAll();
        assertEquals(2, result.size());
    }

    @Test
    void testFindByNome_ValidResult() {
        String validNome = "John";
        when(clienteRepository.findByNome(validNome)).thenReturn(clientesList);
        List<ClientesDTO> result = clienteService.findByNome(validNome);
        assertFalse(result.isEmpty());
    }



}
