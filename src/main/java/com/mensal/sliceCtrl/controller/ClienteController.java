package com.mensal.sliceCtrl.controller;

import com.mensal.sliceCtrl.DTO.ClientesDTO;
import com.mensal.sliceCtrl.entity.Clientes;
import com.mensal.sliceCtrl.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/cliente")
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
    public ResponseEntity<?> postCliente(@RequestBody @Validated ClientesDTO clientesDTO) {
        try{
            Clientes createdClientes = clienteService.createCliente(clientesDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdClientes);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putCliente(@PathVariable Long id, @RequestBody @Validated ClientesDTO clientesDTO) {
        try {
            Clientes updatedClientes = clienteService.updateCliente(id, clientesDTO);
            return ResponseEntity.ok(updatedClientes);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e);
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
