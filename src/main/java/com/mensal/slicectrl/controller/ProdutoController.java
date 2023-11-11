package com.mensal.slicectrl.controller;

import com.mensal.slicectrl.dto.PizzasDTO;
import com.mensal.slicectrl.dto.ProdutosDTO;
import com.mensal.slicectrl.entity.enums.Categoria;
import com.mensal.slicectrl.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Esta classe representa o controlador para lidar com operações relacionadas a produtos.
 */

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/all")
    public ResponseEntity<List<ProdutosDTO>> getAllProdutos() {
        List<ProdutosDTO> produtosDTOS = produtoService.getAll();
        return ResponseEntity.ok(produtosDTOS);
    }

    @GetMapping("/nome/{nomeProduto}")
    public ResponseEntity<ProdutosDTO> getProdutoByNome(@PathVariable("nomeProduto") String nomeProduto){
        ProdutosDTO produtosDTO = produtoService.getByNome(nomeProduto);
        if (produtosDTO != null) {
            return ResponseEntity.ok(produtosDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/ativo/{ativo}")
    public ResponseEntity<List<ProdutosDTO>> getAllByAtivo(@PathVariable boolean ativo){
        try {
            List<ProdutosDTO> ProdutosDTO = produtoService.findByAtivo(ativo);
            return ResponseEntity.ok(ProdutosDTO);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Ouve algum erro.");
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ProdutosDTO> getProdutoById(@PathVariable("id") Long id){
        ProdutosDTO produtosDTO = produtoService.getById(id);
        if (produtosDTO != null) {
            return ResponseEntity.ok(produtosDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/categoria/{categoriaName}")
    public ResponseEntity<List<ProdutosDTO>> getProductsByCategoria(@PathVariable String categoriaName) {
        try {
            Categoria categoria = Categoria.valueOf(categoriaName);
            List<ProdutosDTO> produtosDTOS = produtoService.getByCategoria(categoria);
            return ResponseEntity.ok(produtosDTOS);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Categoria Invalida: " + categoriaName);
        }
    }

    @GetMapping("/disponivel")
    public ResponseEntity<List<ProdutosDTO>> getByAvailable(){
        List<ProdutosDTO> produtosDTOS = produtoService.getByDisponivel();
        return ResponseEntity.ok(produtosDTOS);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'FUNCIONARIO')")
    public ResponseEntity<String> cadastrarProduto(@RequestBody @Validated ProdutosDTO produtosDTO) {
        try {
            this.produtoService.cadastrar(produtosDTO);
            return ResponseEntity.ok().body(String.format("O cadastro de '%s' foi realizado com sucesso.",
                    produtosDTO.getNomeProduto()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ocorreu um erro durante o cadastro: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'FUNCIONARIO')")
    public ResponseEntity<String> editarProduto(@PathVariable Long id,
                                                 @RequestBody @Validated ProdutosDTO produtosDTO) {
        try {
            this.produtoService.editar(produtosDTO);
            return ResponseEntity.ok().body(String.format("O cadastro de '%s' foi atualizado com sucesso.",
                    produtosDTO.getNomeProduto()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ocorreu um erro durante a atualização: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'FUNCIONARIO')")
    public ResponseEntity<String> excluirProduto(@PathVariable("id") Long id) {
        try {
            this.produtoService.deletar(id);
            return ResponseEntity.ok().body("Produto excluído com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ocorreu um erro: " + e.getMessage());
        }
    }
    }



