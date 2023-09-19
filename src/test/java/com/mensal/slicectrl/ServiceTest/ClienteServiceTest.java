package com.mensal.slicectrl.ServiceTest;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.mensal.slicectrl.dto.ClientesDTO;
import com.mensal.slicectrl.entity.Clientes;
import com.mensal.slicectrl.repository.ClienteRepository;
import com.mensal.slicectrl.repository.EnderecoRepository;
import com.mensal.slicectrl.service.ClienteService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@SpringBootTest
public class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private EnderecoRepository enderecoRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ClienteService clienteService;

    private List<Clientes> clientesList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        //MockitoAnnotations.openMocks(this);

        Clientes sampleCliente = new Clientes();

        sampleCliente.setId(1L);
        sampleCliente.setNome("John");
        sampleCliente.setCpf("0202938920");


        Mockito.when(clienteRepository.findById(1L)).thenReturn(Optional.of(sampleCliente));
        Mockito.when(modelMapper.map(sampleCliente, ClientesDTO.class)).thenReturn(new ClientesDTO());
    }


    @Test
    public void testFindById_ValidId() {
        ClientesDTO result = clienteService.findById(1L);
        assertNotNull(result);
    }

    @Test
    public void testFindById_InvalidId() {
        when(clienteRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> clienteService.findById(2L));
    }

    @Test
    public void testGetAll() {

        Clientes sampleCliente1 = new Clientes();
        sampleCliente1.setId(1L);
        sampleCliente1.setNome("John");
        sampleCliente1.setCpf("0202938920");

        Clientes sampleCliente2 = new Clientes();
        sampleCliente2.setId(2L);
        sampleCliente2.setNome("Alice");
        sampleCliente2.setCpf("723798932323");

        clientesList.add(sampleCliente1);
        clientesList.add(sampleCliente2);

        // Mock the behavior of ModelMapper to map the entities to DTOs
        when(modelMapper.map(sampleCliente1, ClientesDTO.class)).thenReturn(new ClientesDTO());
        when(modelMapper.map(sampleCliente2, ClientesDTO.class)).thenReturn(new ClientesDTO());

        // Mock the behavior of clienteRepository
        when(clienteRepository.findAll()).thenReturn(clientesList);

        // Test the getAll method
        List<ClientesDTO> result = clienteService.findAll();

        // Assert that the result contains two DTOs
        assertEquals(2, result.size());
    }


    @Test
    public void testFindByNome_ValidResult() {
        Clientes sampleCliente = new Clientes();
        sampleCliente.setId(1L);
        sampleCliente.setNome("John");

        when(clienteRepository.findByNome("John")).thenReturn(List.of(sampleCliente));

        when(modelMapper.map(sampleCliente, ClientesDTO.class)).thenReturn(new ClientesDTO());

        List<ClientesDTO> result = clienteService.findByNome("John");

        assertFalse(result.isEmpty());
    }

}
