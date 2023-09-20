package com.mensal.slicectrl.ServiceTest;

import com.mensal.slicectrl.dto.ProdutosDTO;
import com.mensal.slicectrl.entity.Produtos;
import com.mensal.slicectrl.entity.enums.Categoria;
import com.mensal.slicectrl.repository.ProdutoRepository;
import com.mensal.slicectrl.service.ProdutoService;
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
public class ProdutoServiceTest {
    @InjectMocks
    private ProdutoService service;

    @Mock
    private ProdutoRepository repository;

    @Mock
    private ModelMapper modelMapper;

    static final Produtos produto = new Produtos("Coca", Categoria.BEBIDAS, 10, true, 11.50);

    static final ProdutosDTO produtoDTO = new ProdutosDTO("Coca", Categoria.BEBIDAS, 10, true, 11.50);


    @BeforeEach
    void setUp() {

        List<Produtos> produtosList = new ArrayList<>();
        List<Produtos> produtosListVazia = new ArrayList<>();


        produtosList.add(produto);


        when(repository.findById(1L)).thenReturn(Optional.of(produto));
        when(modelMapper.map(produto, ProdutosDTO.class)).thenReturn(new ProdutosDTO());


        when(repository.findById(2L)).thenReturn(Optional.empty());

        when(repository.findAll()).thenReturn(produtosList);

        when(repository.findByNome("Coca")).thenReturn(produto);

        when(repository.findByNome("Null")).thenReturn(null);

        when(repository.findByCategoria(Categoria.BEBIDAS)).thenReturn(produtosList);

        when(repository.findByCategoria(Categoria.OUTROS)).thenReturn(produtosListVazia);


        when(repository.findByDisponivelTrue()).thenReturn(produtosList);

        Mockito.when(service.toProdutos(produtoDTO)).thenReturn(produto);

        Mockito.when(service.toProdutosDTO(produto)).thenReturn(produtoDTO);

        when(repository.save(produto)).thenReturn(produto);



    }


    @Test
    void testGetByIdProdutoService() {
        assertNotNull(service.getById(1L));
    }

    @Test
    void testGetByIdProdutoServiceNull() {
        assertNull(service.getById(2L));
    }

    @Test
    void testGetAllProdutoService(){
        assertNotNull(service.getAll());
    }

    @Test
    void testGetByCategoriaProdutoService(){
        assertNotNull(service.getByCategoria(Categoria.BEBIDAS));
    }

    @Test
    void testGetByCategoriaProdutoServiceNull(){
        assertTrue(service.getByCategoria(Categoria.OUTROS).isEmpty());
    }


    @Test
    void testGetByDisponivelProdutoService(){
        assertNotNull(service.getByDisponivel());
    }

    @Test
    void testGetByNomeProdutoService() {
        assertNotNull(service.getByNome("Coca"));
    }

    @Test
    void testGetByNomeProdutoServiceNull() {
        assertNull(service.getByNome("Null"));
    }

    @Test
    void testCadastrarProdutoService() {

        Produtos resposta = service.cadastrar(produtoDTO);

        assertNotNull(resposta);
        assertEquals(resposta, produto);

    }



    @Test
    void testEditarProdutoService() {

        Produtos resposta = service.editar(produtoDTO);

        assertNotNull(resposta);
        assertEquals(resposta, produto);

    }

    @Test
    void testEditarProdutoServiceIndisponivel() {
        produtoDTO.setQtdeEstoque(0);
        produto.setDisponivel(false);

        Produtos resposta = service.editar(produtoDTO);

        assertNotNull(resposta);
        assertFalse(resposta.isDisponivel());
        assertEquals(resposta, produto);

    }
}
