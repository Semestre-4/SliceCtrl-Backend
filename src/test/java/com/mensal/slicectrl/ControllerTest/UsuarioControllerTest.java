package com.mensal.slicectrl.ControllerTest;

import com.mensal.slicectrl.controller.UsuarioController;
import com.mensal.slicectrl.dto.UsuarioDTO;
import com.mensal.slicectrl.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UsuarioControllerTest {

    @InjectMocks
    private UsuarioController usuarioController;

    @Mock
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetFuncionarioById() {
        Long funcionarioId = 1L;
        UsuarioDTO funcionarioDTO = new UsuarioDTO();
        funcionarioDTO.setId(funcionarioId);

        // Mocking the behavior of the funcionarioService
        when(usuarioService.findById(funcionarioId)).thenReturn(funcionarioDTO);

        // Chamar o método e verificar o resultado
        ResponseEntity<UsuarioDTO> response = usuarioController.getFuncionarioById(funcionarioId);

        // Verificar se a resposta tem o código de status esperado e o corpo esperado
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(funcionarioDTO, response.getBody());

        // Verificar se o método findById foi chamado exatamente uma vez
        verify(usuarioService, times(1)).findById(funcionarioId);
    }

    @Test
    void testGetFuncionarioByIdNotFound() {
        Long funcionarioId = 1L;

        // Mocking the behavior of the funcionarioService to return null, simulating a not found scenario
        when(usuarioService.findById(funcionarioId)).thenReturn(null);

        // Chamar o método e verificar o resultado
        ResponseEntity<UsuarioDTO> response = usuarioController.getFuncionarioById(funcionarioId);

        // Verificar se a resposta tem o código de status esperado
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        // Verificar se o método findById foi chamado exatamente uma vez
        verify(usuarioService, times(1)).findById(funcionarioId);
    }

    @Test
    void testGetFuncionariosByNome() {
        String nome = "John";
        UsuarioDTO funcionarioDTO1 = new UsuarioDTO();
        funcionarioDTO1.setId(1L);
        funcionarioDTO1.setNome("John Doe");

        UsuarioDTO funcionarioDTO2 = new UsuarioDTO();
        funcionarioDTO2.setId(2L);
        funcionarioDTO2.setNome("John Smith");

        List<UsuarioDTO> funcionarioDTOList = new ArrayList<>();
        funcionarioDTOList.add(funcionarioDTO1);
        funcionarioDTOList.add(funcionarioDTO2);

        // Mocking the behavior of the funcionarioService
        when(usuarioService.findByNome(nome)).thenReturn(funcionarioDTOList);

        // Chamar o método e verificar o resultado
        ResponseEntity<List<UsuarioDTO>> response = usuarioController.getFuncionariosByNome(nome);

        // Verificar se a resposta tem o código de status esperado e o corpo esperado
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(funcionarioDTOList, response.getBody());

        // Verificar se o método findByNome foi chamado exatamente uma vez
        verify(usuarioService, times(1)).findByNome(nome);
    }

    @Test
    void testGetFuncionariosByNomeNotFound() {
        String nome = "NonExistentName";

        // Mocking the behavior of the funcionarioService to return an empty list, simulating a not found scenario
        when(usuarioService.findByNome(nome)).thenReturn(new ArrayList<>());

        // Chamar o método e verificar o resultado
        ResponseEntity<List<UsuarioDTO>> response = usuarioController.getFuncionariosByNome(nome);

        // Verificar se a resposta tem o código de status esperado
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        // Verificar se o método findByNome foi chamado exatamente uma vez
        verify(usuarioService, times(1)).findByNome(nome);
    }

    @Test
    void testGetFuncionarioByCPF() {
        String cpf = "12345678901";
        UsuarioDTO funcionarioDTO = new UsuarioDTO();
        funcionarioDTO.setId(1L);
        funcionarioDTO.setNome("Jane Doe");
        funcionarioDTO.setCpf(cpf);

        // Mocking the behavior of the funcionarioService
        when(usuarioService.findByCPF(cpf)).thenReturn(funcionarioDTO);

        // Chamar o método e verificar o resultado
        ResponseEntity<UsuarioDTO> response = usuarioController.getFuncionarioByCPF(cpf);

        // Verificar se a resposta tem o código de status esperado e o corpo esperado
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(funcionarioDTO, response.getBody());

        // Verificar se o método findByCPF foi chamado exatamente uma vez
        verify(usuarioService, times(1)).findByCPF(cpf);
    }

    @Test
    void testGetFuncionarioByCPFNotFound() {
        String cpf = "NonExistentCPF";

        // Mocking the behavior of the funcionarioService to return null, simulating a not found scenario
        when(usuarioService.findByCPF(cpf)).thenReturn(null);

        // Chamar o método e verificar o resultado
        ResponseEntity<UsuarioDTO> response = usuarioController.getFuncionarioByCPF(cpf);

        // Verificar se a resposta tem o código de status esperado
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        // Verificar se o método findByCPF foi chamado exatamente uma vez
        verify(usuarioService, times(1)).findByCPF(cpf);
    }

    @Test
    void testGetAllFuncionarios() {
        UsuarioDTO funcionarioDTO1 = new UsuarioDTO();
        funcionarioDTO1.setId(1L);
        funcionarioDTO1.setNome("John Doe");

        UsuarioDTO funcionarioDTO2 = new UsuarioDTO();
        funcionarioDTO2.setId(2L);
        funcionarioDTO2.setNome("Jane Smith");

        List<UsuarioDTO> funcionarioDTOList = new ArrayList<>();
        funcionarioDTOList.add(funcionarioDTO1);
        funcionarioDTOList.add(funcionarioDTO2);

        // Mocking the behavior of the funcionarioService
        when(usuarioService.findAll()).thenReturn(funcionarioDTOList);

        // Chamar o método e verificar o resultado
        ResponseEntity<List<UsuarioDTO>> response = usuarioController.getAllFuncionarios();

        // Verificar se a resposta tem o código de status esperado e o corpo esperado
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(funcionarioDTOList, response.getBody());

        // Verificar se o método findAll foi chamado exatamente uma vez
        verify(usuarioService, times(1)).findAll();
    }

    @Test
    void testCadastrarFuncionarioSuccess() {
        UsuarioDTO funcionarioDTO = new UsuarioDTO();
        funcionarioDTO.setNome("John Doe");
        funcionarioDTO.setCpf("12345678901");

        // Chamar o método e verificar o resultado
        ResponseEntity<String> response = usuarioController.cadastrarFuncionario(funcionarioDTO);

        // Verificar se a resposta tem o código de status esperado e o corpo esperado
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("O cadastro de 'John Doe' foi realizado com sucesso.", response.getBody());

        // Verificar se o método createFuncionario foi chamado exatamente uma vez
        verify(usuarioService, times(1)).createFuncionario(funcionarioDTO);
    }

    @Test
    void testCadastrarFuncionarioError() {
        UsuarioDTO funcionarioDTO = new UsuarioDTO(); // Criar um FuncionariosDTO de exemplo

        // Simular uma exceção sendo lançada durante a criação
        when(usuarioService.createFuncionario(funcionarioDTO)).thenThrow(new RuntimeException("Erro durante a criação"));

        // Chamar o método e verificar o resultado
        ResponseEntity<String> response = usuarioController.cadastrarFuncionario(funcionarioDTO);

        // Verificar se a resposta tem o código de status esperado e contém a mensagem de erro esperada
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("Ocorreu um erro durante o cadastro"));

        // Verificar se o método createFuncionario foi chamado exatamente uma vez
        verify(usuarioService, times(1)).createFuncionario(funcionarioDTO);
    }

    @Test
    void testEditarFuncionarioSuccess() {
        Long funcionarioId = 1L;
        UsuarioDTO funcionarioDTO = new UsuarioDTO();
        funcionarioDTO.setId(funcionarioId);
        funcionarioDTO.setNome("John Doe");
        funcionarioDTO.setCpf("12345678901");

        // Chamar o método e verificar o resultado
        ResponseEntity<String> response = usuarioController.editarFuncionario(funcionarioId, funcionarioDTO);

        // Verificar se a resposta tem o código de status esperado e o corpo esperado
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("O cadastro de 'John Doe' foi atualizado com sucesso.", response.getBody());

        // Verificar se o método updateFuncionario foi chamado exatamente uma vez
        verify(usuarioService, times(1)).updateFuncionario(funcionarioId, funcionarioDTO);
    }

    @Test
    void testEditarFuncionarioError() {
        Long funcionarioId = 1L;
        UsuarioDTO funcionarioDTO = new UsuarioDTO(); // Criar um FuncionariosDTO de exemplo

        // Simular uma exceção sendo lançada durante a atualização
        when(usuarioService.updateFuncionario(funcionarioId, funcionarioDTO))
                .thenThrow(new RuntimeException("Erro durante a atualização"));

        // Chamar o método e verificar o resultado
        ResponseEntity<String> response = usuarioController.editarFuncionario(funcionarioId, funcionarioDTO);

        // Verificar se a resposta tem o código de status esperado e contém a mensagem de erro esperada
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertFalse(Objects.requireNonNull(response.getBody()).contains("Ocorreu um erro durante a atualização"));

        // Verificar se o método updateFuncionario foi chamado exatamente uma vez
        verify(usuarioService, times(1)).updateFuncionario(funcionarioId, funcionarioDTO);
    }

    @Test
    void testExcluirFuncionario() {
        Long funcionarioId = 1L;

        // Chamar o método e verificar o resultado
        ResponseEntity<String> response = usuarioController.excluirFuncionario(funcionarioId);

        // Verificar se a resposta tem o código de status esperado e o corpo esperado
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Funcionário excluído com sucesso!", response.getBody());

        // Verificar se o método deleteFuncionario foi chamado exatamente uma vez
        verify(usuarioService, times(1)).deleteFuncionario(funcionarioId);
    }
}
