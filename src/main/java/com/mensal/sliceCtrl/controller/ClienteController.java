package com.mensal.sliceCtrl.controller;

import com.mensal.sliceCtrl.DTO.ClientesDTO;
import com.mensal.sliceCtrl.entity.Clientes;
import com.mensal.sliceCtrl.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Esta classe representa a controller para lidar com operações CRUD relacionadas a clientes.
 */

@RestController
@RequestMapping("api/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    /**
     * Recupera um cliente pelo seu ID.
     *
     * @param id O ID do cliente a ser recuperado.
     * @return ResponseEntity contendo as informações do cliente, se encontrado, ou uma resposta de "não encontrado".
     */
    @GetMapping("/id/{id}")
    public ResponseEntity<ClientesDTO> getClienteById(@PathVariable("id") Long id) {
        ClientesDTO cliente = clienteService.findById(id);
        if (cliente != null) {
            return ResponseEntity.ok(cliente);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Recupera uma lista de clientes pelo nome.
     *
     * @param nome O nome dos clientes a serem recuperados.
     * @return ResponseEntity contendo a lista de clientes, se encontrados, ou uma resposta de "não encontrado".
     */
    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<ClientesDTO>> getClientesByNome(@PathVariable("nome") String nome) {
        List<ClientesDTO> clientes = clienteService.findByNome(nome);
        if (!clientes.isEmpty()) {
            return ResponseEntity.ok(clientes);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Recupera um cliente pelo CPF.
     *
     * @param cpf O CPF do cliente a ser recuperado.
     * @return ResponseEntity contendo as informações do cliente, se encontrado, ou uma resposta de "não encontrado".
     */
    @GetMapping("/{cpf}")
    public ResponseEntity<ClientesDTO> getClientesByCPF(@PathVariable("cpf") String cpf) {
        ClientesDTO cliente = clienteService.findByCPF(cpf);
        if (cliente != null) {
            return ResponseEntity.ok(cliente);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Recupera uma lista de todos os clientes.
     *
     * @return ResponseEntity contendo a lista de todos os clientes.
     */
    @GetMapping("/all")
    public ResponseEntity<List<ClientesDTO>> getAllClientes() {
        List<ClientesDTO> clientes = clienteService.findAll();
        return ResponseEntity.ok(clientes);
    }

    /**
     * Cria um novo cliente.
     *
     * @param clientesDTO Os dados do cliente a serem criados.
     * @return ResponseEntity indicando o sucesso ou a falha da criação.
     */
    @PostMapping
    public ResponseEntity<String> cadastrarCliente(@RequestBody @Validated ClientesDTO clientesDTO) {
        try {
            Clientes createdClientes = clienteService.createCliente(clientesDTO);
            return ResponseEntity.ok(String.format("O cadastro de '%s' foi realizado com sucesso.",
                    clientesDTO.getNome()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ocorreu um erro durante o cadastro: "
                    + e.getCause().getCause().getMessage());
        }
    }

    /**
     * Atualiza as informações de um cliente.
     *
     * @param id          O ID do cliente a ser atualizado.
     * @param clientesDTO Os dados atualizados do cliente.
     * @return ResponseEntity indicando o sucesso ou a falha da atualização.
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> putCliente(@PathVariable Long id, @RequestBody @Validated ClientesDTO clientesDTO) {
        try {
            Clientes updatedClientes = clienteService.updateCliente(id, clientesDTO);
            return ResponseEntity.ok(String.format("O cadastro de '%s' foi atualizado com sucesso.",
                    clientesDTO.getNome()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ocorreu um erro durante a atualização: "
                    + e.getCause().getCause().getMessage());
        }
    }

    /**
     * Exclui um cliente pelo seu ID.
     *
     * @param id O ID do cliente a ser excluído.
     * @return ResponseEntity indicando o sucesso ou a falha da exclusão.
     */
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
