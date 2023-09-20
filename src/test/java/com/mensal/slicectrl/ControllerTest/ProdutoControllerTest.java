package com.mensal.slicectrl.ControllerTest;

import com.mensal.slicectrl.controller.ProdutoController;
import com.mensal.slicectrl.dto.ProdutosDTO;import com.mensal.slicectrl.entity.enums.Categoria;
import com.mensal.slicectrl.service.ProdutoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
@SpringBootTest
class ProdutoControllerTest {

    @InjectMocks
    private ProdutoController controller;

    @Mock
    private ProdutoService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testGetProdutosById() {
        Long produtoId = 1L;
        ProdutosDTO produtosDTO = new ProdutosDTO();

        Mockito.when(service.getById(produtoId)).thenReturn(produtosDTO);

        ResponseEntity<ProdutosDTO> response = controller.getProdutoById(produtoId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(produtosDTO, response.getBody());

        verify(service, times(1)).getById(produtoId);
    }

    @Test
    void testGetProdutosByIdNotFound() {

        Mockito.when(service.getById(1L)).thenReturn(null);

        ResponseEntity<ProdutosDTO> response = controller.getProdutoById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(service, times(1)).getById(1L);
    }

    @Test
    void testGetAllProdutos(){

        List<ProdutosDTO> produtosDTOList = new ArrayList<>();

        produtosDTOList.add(new ProdutosDTO());

        Mockito.when(service.getAll()).thenReturn(produtosDTOList);

        ResponseEntity<List<ProdutosDTO>> response = controller.getAllProdutos();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(produtosDTOList, response.getBody());

        verify(service, times(1)).getAll();
    }

    @Test
    void testGetByNomeProduto() {
        String nome = "Coca";
        ProdutosDTO produtosDTO = new ProdutosDTO("Coca", Categoria.BEBIDAS, 100, true, 100.00);

        Mockito.when(service.getByNome(nome)).thenReturn(produtosDTO);

        ResponseEntity<ProdutosDTO> response = controller.getProdutoByNome(nome);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(produtosDTO, response.getBody());

        verify(service, times(1)).getByNome(nome);

    }

    @Test
    void testGetByCategoriaProduto() {
        ProdutosDTO produtosDTO = new ProdutosDTO("Coca", Categoria.BEBIDAS, 100, true, 100.00);
        List<ProdutosDTO> produtosDTOList = new ArrayList<>();
        produtosDTOList.add(produtosDTO);

        Mockito.when(service.getByCategoria(Categoria.BEBIDAS)).thenReturn(produtosDTOList);

        ResponseEntity<List<ProdutosDTO>> response = controller.getProductsByCategoria("BEBIDAS");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(produtosDTOList, response.getBody());

        verify(service, times(1)).getByCategoria(Categoria.BEBIDAS);

    }

    @Test
    void testGetByDisponivelProduto() {
        ProdutosDTO produtosDTO = new ProdutosDTO("Coca", Categoria.BEBIDAS, 100, true, 100.00);
        List<ProdutosDTO> produtosDTOList = new ArrayList<>();
        produtosDTOList.add(produtosDTO);

        Mockito.when(service.getByDisponivel()).thenReturn(produtosDTOList);

        ResponseEntity<List<ProdutosDTO>> response = controller.getByAvailable();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(produtosDTOList, response.getBody());

        verify(service, times(1)).getByDisponivel();

    }



    @Test
    void testGetByNomeProdutoNotFound() {
        String nome = "Coca";

        Mockito.when(service.getByNome(nome)).thenReturn(null);

        ResponseEntity<ProdutosDTO> response = controller.getProdutoByNome(nome);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(service, times(1)).getByNome(nome);

    }

    @Test
    void testCadastrarProdutos(){
        ProdutosDTO produtosDTO = new ProdutosDTO("Coca", Categoria.BEBIDAS, 100, true, 100.00);

        ResponseEntity<String> response = controller.cadastrarProduto(produtosDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("O cadastro de 'Coca' foi realizado com sucesso.", response.getBody());

        verify(service, times(1)).cadastrar(produtosDTO);

    }

    @Test
    void testCadastrarProdutosError(){

        ResponseEntity<String> response = controller.cadastrarProduto(null);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(service, times(1)).cadastrar(null);
    }

    @Test
    void testEditProdutos(){
        ProdutosDTO produtosDTO = new ProdutosDTO("CocaEditado", Categoria.BEBIDAS, 100, true, 100.00);

        ResponseEntity<String> response = controller.editarProduto(1L, produtosDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("O cadastro de 'CocaEditado' foi atualizado com sucesso.", response.getBody());

        verify(service, times(1)).editar(produtosDTO);

    }

    @Test
    void testEditProdutosError(){

        ResponseEntity<String> response = controller.editarProduto(1L, null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        verify(service, times(1)).editar(null);

    }

    @Test
    void testDeleteProdutos(){
        Long produtoId = 1L;

        ResponseEntity<String> response = controller.excluirProduto(produtoId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Produto excluído com sucesso!", response.getBody());

        verify(service, times(1)).deletar(produtoId);

    }



}
