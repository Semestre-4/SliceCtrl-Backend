package com.mensal.sliceCtrl.controller;

import com.mensal.sliceCtrl.DTO.ProdutosDTO;
import com.mensal.sliceCtrl.entity.Produtos;
import com.mensal.sliceCtrl.entity.enums.Categorias;
import com.mensal.sliceCtrl.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/all")
    private List<ProdutosDTO> getAll(){
        return this.produtoService.getAll();
    }

    @GetMapping("/nome={nomeProduto}")
    private ProdutosDTO getByNome(@PathVariable("nomeProduto") String nomeProduto){
        return this.produtoService.getByNome(nomeProduto);
    }

    @GetMapping("/{id}")
    private ProdutosDTO getByNome(@PathVariable("id") Long id){
        return this.produtoService.getById(id);
    }

    @GetMapping("/categoria={categoria}")
    private ProdutosDTO getByCategoria(@PathVariable("categoria") String categoria){
        return  this.produtoService.getByCategoria(categoria);
    }

    @GetMapping("/disponivel={disponivel}")
    private List<ProdutosDTO> getByCategoria(@PathVariable("disponivel") Boolean disponivel){
        return  this.produtoService.getByDisponivel(disponivel);
    }

    @PostMapping
    private ResponseEntity<String> cadastrar(@RequestBody ProdutosDTO produtosDTO){
     try{
         this.produtoService.cadastrar(produtosDTO);
         return ResponseEntity.ok().body("Cadastrado com sucesso!");
     }catch (Exception e){
         return ResponseEntity.badRequest().body(e.getMessage());
     }
    }

    @PutMapping
    private ResponseEntity<String> editar(@RequestBody ProdutosDTO produtosDTO){
        try {
            this.produtoService.editar(produtosDTO);
            return ResponseEntity.ok().body("Editado com sucesso!");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping
    private ResponseEntity<String> deletar(@RequestParam("id") Long id){
        try{
            this.produtoService.deletar(id);
            return ResponseEntity.ok().body("Deletado com sucesso!");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    }



