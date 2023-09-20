package com.mensal.slicectrl.ServiceTest;

import com.mensal.slicectrl.dto.FuncionariosDTO;
import com.mensal.slicectrl.entity.Funcionarios;
import com.mensal.slicectrl.repository.FuncionarioRepository;
import com.mensal.slicectrl.service.FuncionarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

@SpringBootTest
public class FuncionarioServiceTest {

    @Mock
    private FuncionarioRepository funcionarioRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private FuncionarioService funcionarioService;

    private List<Funcionarios> funcionariosList = new ArrayList<>();
    Funcionarios funcionarios = new Funcionarios();
    Funcionarios funcionarios2 = new Funcionarios();

    @BeforeEach
    void setUp(){
        funcionarios.setId(1L);
        funcionarios.setNome("John");
        funcionarios.setCpf("0202938920");

        funcionarios2.setId(2L);
        funcionarios2.setNome("Alice");
        funcionarios2.setCpf("723798932323");

        funcionariosList.add(funcionarios);
        funcionariosList.add(funcionarios2);

        when(funcionarioRepository.findById(1L)).thenReturn(Optional.of(funcionarios));
        when(funcionarioRepository.findById(2L)).thenReturn(Optional.empty());
        when(funcionarioRepository.findAll()).thenReturn(funcionariosList);
        when(funcionarioRepository.findByNome("John")).thenReturn(List.of(funcionarios));
        when(funcionarioRepository.findByCpf("0202938920")).thenReturn(funcionarios);
        when(modelMapper.map(funcionarios, FuncionariosDTO.class)).thenReturn(new FuncionariosDTO());
        when(modelMapper.map(funcionarios, FuncionariosDTO.class)).thenReturn(new FuncionariosDTO());
        when(modelMapper.map(funcionarios2, FuncionariosDTO.class)).thenReturn(new FuncionariosDTO());
    }

    @Test
    public void testFindById_ValidId() {
        FuncionariosDTO result = funcionarioService.findById(1L);
        assertNotNull(result);
    }

    @Test
    public void testFindById_InvalidId() {
        assertThrows(IllegalArgumentException.class, () -> funcionarioService.findById(2L));
    }

    @Test
    public void testGetAll() {
        List<FuncionariosDTO> result = funcionarioService.findAll();
        assertEquals(2, result.size());
    }

    @Test
    public void testFindByNome_ValidResult() {
        String validNome = "John";
        when(funcionarioRepository.findByNome(validNome)).thenReturn(funcionariosList);
        List<FuncionariosDTO> result = funcionarioService.findByNome(validNome);
        assertFalse(result.isEmpty());
    }


}
