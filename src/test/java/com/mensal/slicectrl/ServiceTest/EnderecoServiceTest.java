package com.mensal.slicectrl.ServiceTest;

import com.mensal.slicectrl.dto.ClientesDTO;
import com.mensal.slicectrl.dto.EnderecosDTO;
import com.mensal.slicectrl.entity.Enderecos;
import com.mensal.slicectrl.repository.EnderecoRepository;
import com.mensal.slicectrl.service.EnderecoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.util.Assert;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class EnderecoServiceTest {

    @Mock
    private EnderecoRepository repository;

    @Mock
    private ModelMapper modelMapper;


    @InjectMocks
    private EnderecoService service;

    @BeforeEach
    void setUp() {
        String cep = "85857730";
        List<Enderecos> enderecosList= new ArrayList<>();

        Enderecos enderecos = new Enderecos("rua", 123, "complemento", "bairro", "cidade", "PR", "Brazil", "85857730");

        enderecosList.add(enderecos);

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(enderecos));
        Mockito.when(modelMapper.map(enderecos, EnderecosDTO.class)).thenReturn(new EnderecosDTO());

        Mockito.when(repository.findById(2L)).thenReturn(Optional.empty());

        Mockito.when(repository.findAll()).thenReturn(enderecosList);

        Mockito.when(repository.findByCep(cep)).thenReturn(enderecosList);

        Mockito.when(repository.save(enderecos)).thenReturn(enderecos);


    }

    @Test
    void testGetByIdEnderecoService(){
        assertNotNull(service.getById(1L));
    }

    @Test
    void testGetByIdEnderecoServiceNull(){
        assertNull(service.getById(2L));
    }

    @Test
    void testGetALlEnderecoService(){
        assertNotNull(service.getAll());
    }

    @Test
    void testGetByCepEnderecoService(){
        assertNotNull(service.getByCep("85857730"));
    }

//    @Test
//    void testCadastrarEnderecoService(){
//        EnderecosDTO enderecosDTO = new EnderecosDTO("rua", 123, "complemento", "bairro", "cidade", "PR", "Brazil", "85857730");
//
//        Enderecos resposta = service.cadastrar(enderecosDTO);
//
//        assertTrue(resposta.getRua().contains("rua"));
//
//    }




}
