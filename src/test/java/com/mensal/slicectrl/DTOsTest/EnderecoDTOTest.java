package com.mensal.slicectrl.DTOsTest;

import com.mensal.slicectrl.dto.ClientesDTO;
import com.mensal.slicectrl.dto.EnderecosDTO;
import com.mensal.slicectrl.entity.Clientes;
import com.mensal.slicectrl.entity.Enderecos;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class EnderecoDTOTest {

    private EnderecosDTO enderecosDTO;

    @BeforeEach
    void setUp() {
        enderecosDTO = new EnderecosDTO();
//        enderecosDTO.setRua("123 Main St");
//        enderecosDTO.setNumero(456);
//        enderecosDTO.setComplemento("Apt 101");
//        enderecosDTO.setBairro("Downtown");
//        enderecosDTO.setCidade("Cityville");
//        enderecosDTO.setEstado("ST");
//        enderecosDTO.setPais("Country");
//        enderecosDTO.setCep("12345-678");
    }

    @Test
    void testValidEnderecoDTO() {
        enderecosDTO.setRua("123 Main St");
        enderecosDTO.setNumero(456);
        enderecosDTO.setComplemento("Apt 101");
        enderecosDTO.setBairro("Downtown");
        enderecosDTO.setCidade("Cityville");
        enderecosDTO.setEstado("ST");
        enderecosDTO.setPais("Country");
        enderecosDTO.setCep("12345-678");

        assertDoesNotThrow(() -> validateEnderecoDTO(enderecosDTO));
    }

    void validateEnderecoDTO(EnderecosDTO dto) {
        if (dto.getRua() == null || dto.getRua().length() < 2 || dto.getRua().length() > 100) {
            throw new IllegalArgumentException("Invalid rua");
        }

        if (dto.getCep() == null || dto.getCep().length() < 2 || dto.getCep().length() > 100) {
            throw new IllegalArgumentException("Invalid cep");
        }

        if (dto.getCidade() == null || dto.getCidade().length() < 2 || dto.getCidade().length() > 100) {
            throw new IllegalArgumentException("Invalid cidade");
        }

        if (dto.getEstado() == null || dto.getEstado().length() < 2 || dto.getEstado().length() > 4) {
            throw new IllegalArgumentException("Invalid estado");
        }

        if (dto.getBairro() == null || dto.getBairro().length() < 2 || dto.getBairro().length() > 50) {
            throw new IllegalArgumentException("Invalid estado");
        }

        if (dto.getPais() == null || dto.getPais().length() < 2 || dto.getPais().length() > 50) {
            throw new IllegalArgumentException("Invalid estado");
        }


    }






}
