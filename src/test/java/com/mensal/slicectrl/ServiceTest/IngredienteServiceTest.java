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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class IngredienteServiceTest {

    static final IngredientesDTO ingredientesDTO = new IngredientesDTO("Mussarelas", 200);

    static final Ingredientes ingredientes = new Ingredientes("Mussarelas", 200);

    @Mock
    private IngredienteRepository repository;

    @InjectMocks
    private IngredienteService service;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {

        List<Ingredientes> ingredientesList= new ArrayList<>();



        ingredientesList.add(ingredientes);

        ingredientes.setId(1L);



        when(repository.findById(1L)).thenReturn(Optional.of(ingredientes));
        when(modelMapper.map(ingredientes, IngredientesDTO.class)).thenReturn(new IngredientesDTO());


        when(repository.findById(2L)).thenReturn(Optional.empty());

        when(repository.findById(7L)).thenReturn(Optional.empty());


        when(repository.findAll()).thenReturn(ingredientesList);

        when(repository.findByNome("Mussarela")).thenReturn(ingredientes);

        when(repository.findByNome("Null")).thenReturn(null);

        Mockito.when(service.toIngredientes(ingredientesDTO)).thenReturn(ingredientes);

        when(repository.save(ingredientes)).thenReturn(ingredientes);


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

    @Test
    void testCadastrarIngredienteService(){

        Ingredientes resposta = service.cadastrar(ingredientesDTO);

        assertNotNull(resposta);
        assertEquals(ingredientes,resposta);

    }

    @Test
    void testEditarIngredienteService(){

        ingredientesDTO.setId(1L);

        Ingredientes resposta = service.editar(ingredientesDTO, 1L);

        assertNotNull(resposta);
        assertEquals(ingredientes, resposta);

    }

    @Test
    void testEditarIngredienteServiceInexistente(){

        assertThrows(IllegalArgumentException.class, () -> service.editar(ingredientesDTO, 7L));

    }

    @Test
    void testEditarIngredienteServiceIdDiferente(){
        ingredientesDTO.setId(3L);

        assertThrows(IllegalArgumentException.class, () -> service.editar(ingredientesDTO, 1L));

    }



}
