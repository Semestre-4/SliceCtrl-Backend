package com.mensal.slicectrl.ServiceTest;

import com.mensal.slicectrl.dto.IngredientesDTO;
import com.mensal.slicectrl.entity.Ingredientes;
import com.mensal.slicectrl.repository.IngredienteRepository;
import com.mensal.slicectrl.service.IngredienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@SpringBootTest
public class IngredienteServiceTest {

    @Mock
    private IngredienteRepository repository;

    @InjectMocks
    private IngredienteService service;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {

        List<Ingredientes> ingredientesList= new ArrayList<>();
        Ingredientes ingredientes = new Ingredientes("Mussarela", 200);

        ingredientesList.add(ingredientes);

        ingredientes.setId(1L);



        when(repository.findById(1L)).thenReturn(Optional.of(ingredientes));
        when(modelMapper.map(ingredientes, IngredientesDTO.class)).thenReturn(new IngredientesDTO());


        when(repository.findById(2L)).thenReturn(Optional.empty());

        when(repository.findAll()).thenReturn(ingredientesList);

        when(repository.findByNome("Mussarela")).thenReturn(ingredientes);

        when(repository.findByNome("Null")).thenReturn(null);


    }

    @Test
    void testGetByIdIngredienteService(){
        assertNotNull(service.getById(1L));
    }

    @Test
    void testGetByIdIngredienteServiceNull(){
        assertNull(service.getById(2L));
    }

    @Test
    void testGetAllIngredienteService(){
        assertNotNull(service.getAll());
    }

    @Test
    void testGetByNomeIngredienteService(){
        assertNotNull(service.getByNome("Mussarela"));
    }

    @Test
    void testGetByNomeIngredienteServiceNull(){
        assertNull(service.getByNome("Null"));
    }





}
