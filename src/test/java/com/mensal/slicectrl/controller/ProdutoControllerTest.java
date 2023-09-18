package com.mensal.slicectrl.controller;

import com.mensal.slicectrl.dto.IngredientesDTO;
import com.mensal.slicectrl.dto.ProdutosDTO;
import com.mensal.slicectrl.service.IngredienteService;
import com.mensal.slicectrl.service.ProdutoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ProdutoControllerTest {

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


}
