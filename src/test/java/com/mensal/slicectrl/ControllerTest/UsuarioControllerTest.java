package com.mensal.slicectrl.ControllerTest;

import com.mensal.slicectrl.controller.UsuarioController;
import com.mensal.slicectrl.dto.UsuarioDTO;
import com.mensal.slicectrl.dto.UsuarioFrontDTO;
import com.mensal.slicectrl.entity.Usuario;
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
        UsuarioFrontDTO funcionarioDTO = new UsuarioFrontDTO();
        funcionarioDTO.setCpf("123456789");

        // Mocking the behavior of the funcionarioService
        when(usuarioService.findById(funcionarioId)).thenReturn(funcionarioDTO);

        // Chamar o método e verificar o resultado
        ResponseEntity<UsuarioFrontDTO> response = usuarioController.getFuncionarioById(funcionarioId);

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
        ResponseEntity<UsuarioFrontDTO> response = usuarioController.getFuncionarioById(funcionarioId);

        // Verificar se a resposta tem o código de status esperado
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        // Verificar se o método findById foi chamado exatamente uma vez
        verify(usuarioService, times(1)).findById(funcionarioId);
    }

    @Test
    void testGetFuncionariosByNome() {
        String nome = "John";
        UsuarioFrontDTO funcionarioDTO1 = new UsuarioFrontDTO();
        funcionarioDTO1.setCpf("123456789");

        UsuarioFrontDTO funcionarioDTO2 = new UsuarioFrontDTO();
        funcionarioDTO2.setCpf("123456789");

        List<UsuarioFrontDTO> funcionarioDTOList = new ArrayList<>();
        funcionarioDTOList.add(funcionarioDTO1);
        funcionarioDTOList.add(funcionarioDTO2);

        // Mocking the behavior of the funcionarioService
        when(usuarioService.findByNome(nome)).thenReturn(funcionarioDTOList);

        // Chamar o método e verificar o resultado
        ResponseEntity<List<UsuarioFrontDTO>> response = usuarioController.getFuncionariosByNome(nome);

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
        ResponseEntity<List<UsuarioFrontDTO>> response = usuarioController.getFuncionariosByNome(nome);

        // Verificar se a resposta tem o código de status esperado
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        // Verificar se o método findByNome foi chamado exatamente uma vez
        verify(usuarioService, times(1)).findByNome(nome);
    }

    @Test
    void testGetFuncionarioByCPF() {
        String cpf = "12345678901";
        UsuarioFrontDTO funcionarioDTO = new UsuarioFrontDTO();
        funcionarioDTO.setCpf(cpf);

        // Mocking the behavior of the funcionarioService
        when(usuarioService.findByCPF(cpf)).thenReturn(funcionarioDTO);

        // Chamar o método e verificar o resultado
        ResponseEntity<UsuarioFrontDTO> response = usuarioController.getFuncionarioByCPF(cpf);

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
        ResponseEntity<UsuarioFrontDTO> response = usuarioController.getFuncionarioByCPF(cpf);

        // Verificar se a resposta tem o código de status esperado
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        // Verificar se o método findByCPF foi chamado exatamente uma vez
        verify(usuarioService, times(1)).findByCPF(cpf);
    }

    @Test
    void testGetAllFuncionarios() {
        UsuarioFrontDTO funcionarioDTO1 = new UsuarioFrontDTO();
        funcionarioDTO1.setCpf("123456789");

        UsuarioFrontDTO funcionarioDTO2 = new UsuarioFrontDTO();
        funcionarioDTO2.setCpf("123456789");

        List<UsuarioFrontDTO> funcionarioDTOList = new ArrayList<>();
        funcionarioDTOList.add(funcionarioDTO1);
        funcionarioDTOList.add(funcionarioDTO2);

        // Mocking the behavior of the funcionarioService
        when(usuarioService.findAll()).thenReturn(funcionarioDTOList);

        // Chamar o método e verificar o resultado
        ResponseEntity<List<UsuarioFrontDTO>> response = usuarioController.getAllFuncionarios();

        // Verificar se a resposta tem o código de status esperado e o corpo esperado
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(funcionarioDTOList, response.getBody());

        // Verificar se o método findAll foi chamado exatamente uma vez
        verify(usuarioService, times(1)).findAll();
    }

    @Test
    void testCadastrarFuncionarioSuccess() {
        Usuario funcionario = new Usuario();
        funcionario.setCpf("12345678901");

        // Chamar o método e verificar o resultado
        ResponseEntity<String> response = usuarioController.cadastrarUsuario(funcionario);

        // Verificar se a resposta tem o código de status esperado e o corpo esperado
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verificar se o método createFuncionario foi chamado exatamente uma vez
        verify(usuarioService, times(1)).createUsuario(funcionario);
    }

    @Test
    void testCadastrarFuncionarioError() {
        Usuario funcionario = new Usuario(); // Criar um FuncionariosDTO de exemplo

        // Simular uma exceção sendo lançada durante a criação
        when(usuarioService.createUsuario(funcionario)).thenThrow(new RuntimeException("Erro durante a criação"));

        // Chamar o método e verificar o resultado
        ResponseEntity<String> response = usuarioController.cadastrarUsuario(funcionario);

        // Verificar se a resposta tem o código de status esperado e contém a mensagem de erro esperada
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("Ocorreu um erro durante o cadastro"));

        // Verificar se o método createFuncionario foi chamado exatamente uma vez
        verify(usuarioService, times(1)).createUsuario(funcionario);
    }

    @Test
    void testEditarFuncionarioSuccess() {
        Long funcionarioId = 1L;
        Usuario funcionario = new Usuario();
        funcionario.setId(funcionarioId);
        funcionario.setCpf("12345678901");

        // Chamar o método e verificar o resultado
        ResponseEntity<String> response = usuarioController.editarUsuario(funcionarioId, funcionario);

        // Verificar se a resposta tem o código de status esperado e o corpo esperado
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verificar se o método updateFuncionario foi chamado exatamente uma vez
        verify(usuarioService, times(1)).updateUsuario(funcionarioId, funcionario);
    }

    @Test
    void testEditarFuncionarioError() {
        Long funcionarioId = 1L;
        Usuario funcionarioDTO = new Usuario(); // Criar um FuncionariosDTO de exemplo

        // Simular uma exceção sendo lançada durante a atualização
        when(usuarioService.updateUsuario(funcionarioId, funcionarioDTO))
                .thenThrow(new RuntimeException("Erro durante a atualização"));

        // Chamar o método e verificar o resultado
        ResponseEntity<String> response = usuarioController.editarUsuario(funcionarioId, funcionarioDTO);

        // Verificar se a resposta tem o código de status esperado e contém a mensagem de erro esperada
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertFalse(Objects.requireNonNull(response.getBody()).contains("Ocorreu um erro durante a atualização"));

        // Verificar se o método updateFuncionario foi chamado exatamente uma vez
        verify(usuarioService, times(1)).updateUsuario(funcionarioId, funcionarioDTO);
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
