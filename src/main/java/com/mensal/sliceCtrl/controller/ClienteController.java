package com.mensal.sliceCtrl.controller;

import com.mensal.sliceCtrl.DTO.ClienteDTO;
import com.mensal.sliceCtrl.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> getClienteById(@PathVariable("id") Long id) {
        ClienteDTO cliente = clienteService.findById(id);
        if (cliente != null) {
            return ResponseEntity.ok(cliente);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{nome}")
    public ResponseEntity<List<ClienteDTO>> getClientesByNome(@PathVariable("nome") String nome) {
        List<ClienteDTO> clientes = clienteService.findByNome(nome);
        if (!clientes.isEmpty()) {
            return ResponseEntity.ok(clientes);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<ClienteDTO> getClientesByCPF(@PathVariable("cpf") String cpf) {
        ClienteDTO cliente = clienteService.findByCPF(cpf);
        if (cliente != null) {
            return ResponseEntity.ok(cliente);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<ClienteDTO>> getAllClientes() {
        List<ClienteDTO> clientes = clienteService.findAll();
        return ResponseEntity.ok(clientes);
    }

    @PostMapping
    public ResponseEntity<ClienteDTO> postCliente(@RequestBody ClienteDTO clienteDTO) {
        ClienteDTO createdCliente = clienteService.createCliente(clienteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> putCliente(@PathVariable Long id, @RequestBody ClienteDTO clienteDTO) {
        ClienteDTO updatedCliente = clienteService.updateCliente(id, clienteDTO);
        if (updatedCliente != null) {
            return ResponseEntity.ok(updatedCliente);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCliente(@PathVariable Long id) {
        try {
            clienteService.deleteCliente(id);
            return ResponseEntity.ok().body("Cliente excluido com sucesso!");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
