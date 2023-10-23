package com.mensal.slicectrl.controller;

import com.mensal.slicectrl.dto.EnderecosDTO;
import com.mensal.slicectrl.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Esta classe representa a controller para lidar com operações relacionadas a endereços.
 */

@RestController
@RequestMapping("/api/enderecos")
public class EnderecoController {

    @Autowired
    public EnderecoService enderecoService;

    /**
     * Recupera um endereço pelo seu ID.
     *
     * @param id O ID do endereço a ser recuperado.
     * @return ResponseEntity contendo as informações do endereço, se encontrado, ou uma resposta de "não encontrado".
     */
    @GetMapping("/id/{id}")
    public ResponseEntity<EnderecosDTO> getEnderecoById(@PathVariable("id") Long id) {
        EnderecosDTO enderecosDTO = enderecoService.getById(id);
        if (enderecosDTO != null) {
            return ResponseEntity.ok(enderecosDTO);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Recupera uma lista de todos os endereços.
     *
     * @return ResponseEntity contendo a lista de todos os endereços.
     */
    @GetMapping("/all")
    public ResponseEntity<List<EnderecosDTO>> getAllEnderecos() {
        List<EnderecosDTO> enderecosDTOS = enderecoService.getAll();
        return ResponseEntity.ok(enderecosDTOS);
    }

    /**
     * Recupera uma lista de endereços pelo CEP.
     *
     * @param cep O CEP dos endereços a serem recuperados.
     * @return ResponseEntity contendo a lista de endereços, se encontrados.
     */
    @GetMapping("/cep/{cep}")
    public ResponseEntity<List<EnderecosDTO>> getByCep(@PathVariable("cep") String cep) {
        List<EnderecosDTO> enderecosDTOS = enderecoService.getByCep(cep);
        return ResponseEntity.ok(enderecosDTOS);
    }

    /**
     * Cria um novo endereço.
     *
     * @param enderecosDTO Os dados do endereço a serem cadastrados.
     * @return ResponseEntity indicando o sucesso ou a falha do cadastro.
     */
    @PostMapping
    public ResponseEntity<String> cadastrarEndereco(@RequestBody @Validated EnderecosDTO enderecosDTO) {
        try {
            this.enderecoService.cadastrar(enderecosDTO);
            return ResponseEntity.ok().body("O cadastro do endereço foi realizado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ocorreu um erro durante o cadastro: " + e.getMessage());
        }
    }

    /**
     * Edita as informações de um endereço.
     *
     * @param id          O ID do endereço a ser editado.
     * @param enderecosDTO Os dados atualizados do endereço.
     * @return ResponseEntity indicando o sucesso ou a falha da edição.
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> editarEndereco( @PathVariable("id") Long id,
                                                  @RequestBody @Validated EnderecosDTO enderecosDTO) {
        try {
            this.enderecoService.editar(id, enderecosDTO);
            return ResponseEntity.ok().body("O cadastro do endereço foi atualizado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ocorreu um erro durante a atualização: " + e.getMessage());
        }
    }

    /**
     * Exclui um endereço pelo seu ID.
     *
     * @param id O ID do endereço a ser excluído.
     * @return ResponseEntity indicando o sucesso ou a falha da exclusão.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirEndereco(@PathVariable("id") Long id) {
        try {
            this.enderecoService.delete(id);
            return ResponseEntity.ok().body("Endereço excluído com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ocorreu um erro: " + e.getMessage());
        }
    }
}
