package com.mensal.sliceCtrl.controller;

import com.mensal.sliceCtrl.DTO.EnderecosDTO;
import com.mensal.sliceCtrl.service.EnderecoService;
import com.sun.source.tree.TryTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @GetMapping("/all")
    private List<EnderecosDTO> getAll (){
        return this.enderecoService.getAll();
    }

    @GetMapping("/{id}")
    private EnderecosDTO getById(@PathVariable("id") Long id){
        return this.enderecoService.getById(id);
    }

    @GetMapping("/cep={cep}")
    private List<EnderecosDTO> getByCep(@PathVariable("cep") String cep){
        return this.enderecoService.getByCep(cep);
    }


    @PostMapping
    private ResponseEntity<String> cadastrar(@RequestBody EnderecosDTO enderecosDTO){
        try{
        this.enderecoService.cadastrar(enderecosDTO);
        return ResponseEntity.ok().body("Cadastrado com sucesso");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }



}
