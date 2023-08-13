package com.mensal.sliceCtrl.controller;

import com.mensal.sliceCtrl.DTO.ProdutosDTO;
import com.mensal.sliceCtrl.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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



    }



