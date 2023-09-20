package com.mensal.slicectrl.ServiceTest;

import com.mensal.slicectrl.dto.FuncionariosDTO;
import com.mensal.slicectrl.dto.PizzasDTO;
import com.mensal.slicectrl.entity.Enderecos;
import com.mensal.slicectrl.entity.Funcionarios;
import com.mensal.slicectrl.entity.enums.Tamanho;
import com.mensal.slicectrl.repository.FuncionarioRepository;
import com.mensal.slicectrl.service.FuncionarioService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
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
    FuncionariosDTO funcionariosDTO = new FuncionariosDTO("Jonh","0202938920","1111", BigDecimal.TEN);
    Funcionarios funcionarios2 = new Funcionarios();

    @BeforeEach
    void setUp(){
        funcionarios.setId(1L);
        funcionarios.setNome("John");
        funcionarios.setCpf("0202938920");
        funcionarios.setTelefone("1111");
        funcionarios.setSalario(BigDecimal.TEN);

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
        when(funcionarioService.toFunc(funcionariosDTO)).thenReturn(funcionarios);
        when(funcionarioRepository.save(funcionarios)).thenReturn(funcionarios);    }

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

    @Test
    void testCadastrarFuncService(){
        Funcionarios resposta = funcionarioService.createFuncionario(funcionariosDTO);
        assertNotNull(resposta);
        assertEquals(resposta, funcionarios);
    }

    @Test
    void testUpdateFuncWhenFuncNotFound() {
        Long id = 1L;
        when(funcionarioRepository.existsById(id)).thenReturn(false);

        FuncionariosDTO funcionariosDTO1 = new FuncionariosDTO("Jonh","0202938920","1111", BigDecimal.TEN);

        assertThrows(EntityNotFoundException.class, () -> {
            funcionarioService.updateFuncionario(id, funcionariosDTO1);
        });
    }

    @Test
    void testUpdateFuncWhenIdMismatch() {
        Long id = 1L;
        when(funcionarioRepository.existsById(id)).thenReturn(true);
        FuncionariosDTO funcionariosDTO1 = new FuncionariosDTO("Jonh", "0202938920", "1111", BigDecimal.TEN);
        funcionariosDTO1.setId(2L);

        assertThrows(IllegalArgumentException.class, () -> {
            funcionarioService.updateFuncionario(id, funcionariosDTO1);
        });
    }



}
