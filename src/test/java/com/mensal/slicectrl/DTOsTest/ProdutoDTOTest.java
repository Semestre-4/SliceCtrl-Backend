package com.mensal.slicectrl.DTOsTest;

import com.mensal.slicectrl.dto.EnderecosDTO;
import com.mensal.slicectrl.dto.ProdutosDTO;
import com.mensal.slicectrl.entity.enums.Categoria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class ProdutoDTOTest {

    private ProdutosDTO produtosDTO;

    @BeforeEach
    void setUp() {
        produtosDTO = new ProdutosDTO();
    }

    @Test
    void testValidEnderecoDTO() {
        produtosDTO.setCategoria(Categoria.BEBIDAS);
        produtosDTO.setNomeProduto("Coca-cola");
        produtosDTO.setQtdeEstoque(10);
        produtosDTO.setPreco(11.50);
        produtosDTO.setDisponivel(true);

        assertDoesNotThrow(() -> validateEnderecoDTO(produtosDTO));
    }

    void validateEnderecoDTO(ProdutosDTO dto) {
        if (dto.getNomeProduto() == null || dto.getNomeProduto().length() < 2 || dto.getNomeProduto().length() > 50) {
            throw new IllegalArgumentException("Invalid name produto");
        }

        if (dto.getPreco() == null || dto.getPreco() < 0) {
            throw new IllegalArgumentException("Invalid preço");
        }

        if (dto.getCategoria() == null) {
            throw new IllegalArgumentException("Invalid categoria");
        }

        if (dto.getQtdeEstoque() == null || dto.getQtdeEstoque() < 0) {
            throw new IllegalArgumentException("Invalid quantidade de estoque");
        }

    }






}
