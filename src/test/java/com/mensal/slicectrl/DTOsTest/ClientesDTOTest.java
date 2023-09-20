package com.mensal.slicectrl.DTOsTest;

import com.mensal.slicectrl.dto.ClientesDTO;
import com.mensal.slicectrl.dto.EnderecosDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class ClientesDTOTest {

    private ClientesDTO clientesDTO;

    @BeforeEach
    void setUp() {
        clientesDTO = new ClientesDTO();
    }

    @Test
    void testValidClientesDTO() {
        clientesDTO.setNome("John Doe");
        clientesDTO.setCpf("12345678901");
        clientesDTO.setTelefone("123-456-7890");
        clientesDTO.setEmail("johndoe@example.com");

         List<EnderecosDTO> enderecos = new ArrayList<>();
         EnderecosDTO endereco = new EnderecosDTO();
         enderecos.add(endereco);
         clientesDTO.setEnderecos(enderecos);

        assertDoesNotThrow(() -> validateClientesDTO(clientesDTO));
    }

    @Test
    void testInvalidClientesDTO() {
        assertThrows(IllegalArgumentException.class, () -> validateClientesDTO(clientesDTO));
    }

    void validateClientesDTO(ClientesDTO dto) {
        if (dto.getNome() == null || dto.getNome().length() < 2 || dto.getNome().length() > 100) {
            throw new IllegalArgumentException("Invalid nome");
        }

        if (dto.getCpf() == null || dto.getCpf().isEmpty()) {
            throw new IllegalArgumentException("Invalid cpf");
        }

        if (dto.getTelefone() == null || dto.getTelefone().isEmpty()) {
            throw new IllegalArgumentException("Invalid telefone");
        }

        if (dto.getEnderecos() == null || dto.getEnderecos().isEmpty()) {
            throw new IllegalArgumentException("At least one endereco is required");
        }
    }
}
