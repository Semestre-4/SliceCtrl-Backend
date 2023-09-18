package com.mensal.slicectrl.controller;

import com.mensal.slicectrl.dto.EnderecosDTO;
import com.mensal.slicectrl.dto.IngredientesDTO;
import com.mensal.slicectrl.dto.ProdutosDTO;
import com.mensal.slicectrl.dto.SaboresDTO;
import com.mensal.slicectrl.entity.enums.Categoria;
import com.mensal.slicectrl.service.EnderecoService;
import com.mensal.slicectrl.service.SaboresService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class EnderecoControllerTest {

    @InjectMocks
    private EnderecoController controller;

    @Mock
    private EnderecoService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetByIdEndereco() {
        Long enderecoId = 1L;
        EnderecosDTO enderecosDTO = new EnderecosDTO();

        Mockito.when(service.getById(enderecoId)).thenReturn(enderecosDTO);

        ResponseEntity<EnderecosDTO> response = controller.getEnderecoById(enderecoId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(enderecosDTO, response.getBody());

        verify(service, times(1)).getById(enderecoId);
    }

    @Test
    void testGetByIdNotFound() {

        Mockito.when(service.getById(1L)).thenReturn(null);

        ResponseEntity<EnderecosDTO> response = controller.getEnderecoById(1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        verify(service, times(1)).getById(1L);
    }

    @Test
    void testGetByCepEndereco() {
        String cep = "85857730";
        List<EnderecosDTO> enderecosDTOList= new ArrayList<>();
        EnderecosDTO enderecosDTO = new EnderecosDTO("teste", 123, "teste", "teste", "teste", "teste", "PR", "85857730");

        enderecosDTOList.add(enderecosDTO);

        Mockito.when(service.getByCep(cep)).thenReturn(enderecosDTOList);

        ResponseEntity<List<EnderecosDTO>> response = controller.getByCep(cep);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(enderecosDTOList, response.getBody());

        verify(service, times(1)).getByCep(cep);

    }

    @Test
    void testGetAllEndereco(){

        List<EnderecosDTO> enderecosDTOList = new ArrayList<>();

        enderecosDTOList.add(new EnderecosDTO());

        Mockito.when(service.getAll()).thenReturn(enderecosDTOList);

        ResponseEntity<List<EnderecosDTO>> response = controller.getAllEnderecos();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(enderecosDTOList, response.getBody());

        verify(service, times(1)).getAll();
    }

    @Test
    void testCadastrarEndereco(){
        EnderecosDTO enderecosDTO = new EnderecosDTO("teste", 123, "teste", "teste", "teste", "teste", "PR", "85857730");


        ResponseEntity<String> response = controller.cadastrarEndereco(enderecosDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("O cadastro do endereço foi realizado com sucesso.", response.getBody());

        verify(service, times(1)).cadastrar(enderecosDTO);

    }
    @Test
    void testCadastrarEnderecoError(){

        when(service.cadastrar(null)).thenThrow(RuntimeException.class); // Simular uma exceção de tempo de execução
        ResponseEntity<String> response = controller.cadastrarEndereco(null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        verify(service, times(1)).cadastrar(null);

    }

    @Test
    void testEditEndereco(){
        EnderecosDTO enderecosDTO = new EnderecosDTO("teste", 123, "teste", "teste", "teste", "teste", "PR", "85857730");

        ResponseEntity<String> response = controller.editarEndereco(1L, enderecosDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("O cadastro do endereço foi atualizado com sucesso.", response.getBody());

        verify(service, times(1)).editar(enderecosDTO, 1L);

    }

//    @Test
//    void testEditEnderecoError(){
//
//        when(controller.editarEndereco(1L, null)).thenThrow(RuntimeException.class); // Simular uma exceção de tempo de execução
//        ResponseEntity<String> response = controller.cadastrarEndereco(null);
//
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//
//        verify(controller, times(1)).editarEndereco(1L, null);
//
//    }

    @Test
    void testDeleteEndereco(){
        Long enderecoId = 1L;

        ResponseEntity<String> response = controller.excluirEndereco(enderecoId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Endereço excluído com sucesso!", response.getBody());

        verify(service, times(1)).delete(enderecoId);

    }







}
