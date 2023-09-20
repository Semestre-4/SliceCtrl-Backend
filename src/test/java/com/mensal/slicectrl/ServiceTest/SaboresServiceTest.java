package com.mensal.slicectrl.ServiceTest;

import com.mensal.slicectrl.dto.IngredientesDTO;
import com.mensal.slicectrl.dto.SaboresDTO;
import com.mensal.slicectrl.entity.Sabores;
import com.mensal.slicectrl.repository.SaboresRepository;
import com.mensal.slicectrl.service.SaboresService;
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
class SaboresServiceTest {


    static final List<IngredientesDTO> ingredientesDTOList= new ArrayList<>();
    static final SaboresDTO saboresDTO = new SaboresDTO("Teste", ingredientesDTOList);
    static final Sabores sabores = new Sabores("Calabresa","sem cebola", 00.01);

    @Mock
    private SaboresRepository repository;

    @InjectMocks
    private SaboresService service;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {

        List<Sabores> saboresList= new ArrayList<>();



        saboresList.add(sabores);


        when(repository.findById(1L)).thenReturn(Optional.of(sabores));

        when(modelMapper.map(sabores, SaboresDTO.class)).thenReturn(new SaboresDTO());

        when(modelMapper.map(saboresDTO, Sabores.class)).thenReturn(new Sabores());

        when(repository.findById(2L)).thenReturn(Optional.empty());

        when(repository.findAll()).thenReturn(saboresList);

        when(repository.findByNome("Calabresa")).thenReturn(sabores);

        Mockito.when(service.toSabores(saboresDTO)).thenReturn(sabores);

        Mockito.when(service.toSaboresDTO(sabores)).thenReturn(saboresDTO);

        when(repository.save(sabores)).thenReturn(sabores);


    }

    @Test
    void testGetByIdSaboresService(){
        assertNotNull(service.getById(1L));
    }

    @Test
    void testGetByIdSaboresServiceNull(){
        assertThrows(IllegalArgumentException.class, () -> service.getById(2L));
    }

    @Test
    void testGetAllSaboresService(){
        assertNotNull(service.getAll());
    }

    @Test
    void testGetByNomeSaboresService(){
        assertNotNull(service.getByNome("Calabresa"));
    }

    @Test
    void testGetByNomeSaboresServiceNull(){
        assertThrows(IllegalArgumentException.class, () -> service.getByNome("Null"));
    }

    @Test
    void testCadastrarSaboresService(){

        Sabores resposta = service.cadastrar(saboresDTO);

        assertNotNull(resposta);
        assertEquals(sabores, resposta);

    }

    @Test
    void testEditarSaboresService(){

        saboresDTO.setId(1L);

        Sabores resposta = service.editar(1L, saboresDTO);

        assertNotNull(resposta);
        assertEquals(sabores, resposta);

    }

    @Test
    void testEditarSaboresServiceIdDiferentes(){
        saboresDTO.setId(2L);
        assertThrows(IllegalArgumentException.class, () -> service.editar(1L, saboresDTO));

    }

    @Test
    void testEditarSaboresServiceIdInexistente(){
        assertThrows(IllegalArgumentException.class, () -> service.editar(70L, saboresDTO));
    }


}
