package com.mensal.slicectrl.controller;

import com.mensal.slicectrl.dto.ClientesDTO;
import com.mensal.slicectrl.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Esta classe representa a controller para lidar com operações CRUD relacionadas a clientes.
 */

@RestController
@RequestMapping("api/cliente")
@CrossOrigin("http://localhost:4200")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/id/{id}")
    public ResponseEntity<ClientesDTO> getClienteById(@PathVariable("id") Long id) {
        ClientesDTO cliente = clienteService.findById(id);
        if (cliente != null) {
            return ResponseEntity.ok(cliente);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<ClientesDTO>> getClientesByNome(@PathVariable("nome") String nome) {
        List<ClientesDTO> clientes = clienteService.findByNome(nome);
        if (!clientes.isEmpty()) {
            return ResponseEntity.ok(clientes);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/ativo/{ativo}")
    public ResponseEntity<List<ClientesDTO>> getClientesByAtivo(@PathVariable("ativo") boolean ativo) {
        List<ClientesDTO> clientes = clienteService.findByAtivo(ativo);
        if (!clientes.isEmpty()) {
            return ResponseEntity.ok(clientes);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<ClientesDTO> getClientesByCPF(@PathVariable("cpf") String cpf) {
        ClientesDTO cliente = clienteService.findByCPF(cpf);
        if (cliente != null) {
            return ResponseEntity.ok(cliente);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<ClientesDTO>> getAllClientes() {
        List<ClientesDTO> clientes = clienteService.findAll();
        return ResponseEntity.ok(clientes);
    }

    @PostMapping
    public ResponseEntity<String> cadastrarCliente(@RequestBody @Validated ClientesDTO clientesDTO) {
        try {
            clienteService.createCliente(clientesDTO);
            return ResponseEntity.ok(String.format("O cadastro de '%s' foi realizado com sucesso.",
                    clientesDTO.getNome()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ocorreu um erro durante o cadastro: "
                    + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> putCliente(@PathVariable Long id, @RequestBody @Validated ClientesDTO clientesDTO) {
        try {
            clienteService.updateCliente(id, clientesDTO);
            return ResponseEntity.ok(String.format("O cadastro de '%s' foi atualizado com sucesso.",
                    clientesDTO.getNome()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ocorreu um erro durante a atualização: "
                    + e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO_TECNICO')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirCliente(@PathVariable Long id) {
        try {
            clienteService.deleteCliente(id);
            return ResponseEntity.ok().body("Cliente excluído com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ocorreu um erro: " + e.getMessage());
        }
    }
}
