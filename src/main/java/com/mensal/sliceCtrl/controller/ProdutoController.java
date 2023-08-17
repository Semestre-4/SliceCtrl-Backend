package com.mensal.sliceCtrl.controller;

import com.mensal.sliceCtrl.DTO.ProdutosDTO;
import com.mensal.sliceCtrl.entity.enums.Categorias;
import com.mensal.sliceCtrl.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    /**
     * Recupera todos os produtos cadastrados.
     *
     * @return ResponseEntity contendo a lista de produtos ou uma resposta de erro.
     */
    @GetMapping("/all")
    private ResponseEntity<List<ProdutosDTO>> getAllProdutos() {
        List<ProdutosDTO> produtosDTOS = produtoService.getAll();
        return ResponseEntity.ok(produtosDTOS);
    }

    @GetMapping("/nome/{nomeProduto}")
    private ResponseEntity<ProdutosDTO> getProdutoByNome(@PathVariable("nomeProduto") String nomeProduto){
        ProdutosDTO produtosDTO = produtoService.getByNome(nomeProduto);
        if (produtosDTO != null) {
            return ResponseEntity.ok(produtosDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("id/{id}")
    private ResponseEntity<ProdutosDTO> getProdutoById(@PathVariable("id") Long id){
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
            Categorias categoria = Categorias.valueOf(categoriaName);
            List<ProdutosDTO> produtosDTOS = produtoService.findByCategoria(categoria);
            return ResponseEntity.ok(produtosDTOS);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Categoria Invalida: " + categoriaName);
        }
    }

    @GetMapping("/disponivel")
    private ResponseEntity<List<ProdutosDTO>> getByAvailable(){
        List<ProdutosDTO> produtosDTOS = produtoService.findByDisponivel();
        return ResponseEntity.ok(produtosDTOS);
    }

    /**
     * Cria um novo produto.
     *
     * @param produtosDTO Os dados do produto a serem cadastrados.
     * @return ResponseEntity indicando o sucesso ou a falha do cadastro.
     */
    @PostMapping
    private ResponseEntity<String> cadastrarProduto(@RequestBody @Validated ProdutosDTO produtosDTO) {
        try {
            this.produtoService.cadastrar(produtosDTO);
            return ResponseEntity.ok().body(String.format("O cadastro de '%s' foi realizado com sucesso.",
                    produtosDTO.getNomeProduto()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ocorreu um erro durante o cadastro: " + e.getMessage());
        }
    }

    /**
     * Atualiza as informações de um produto.
     *
     * @param id         O ID do produto a ser editado.
     * @param produtosDTO Os dados atualizados do produto.
     * @return ResponseEntity indicando o sucesso ou a falha da edição.
     */
    @PutMapping("/{id}")
    private ResponseEntity<String> editarProduto(@PathVariable Long id,
                                                 @RequestBody @Validated ProdutosDTO produtosDTO) {
        try {
            this.produtoService.editar(produtosDTO);
            return ResponseEntity.ok().body(String.format("O cadastro de '%s' foi atualizado com sucesso.",
                    produtosDTO.getNomeProduto()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ocorreu um erro durante a atualização: " + e.getMessage());
        }
    }

    /**
     * Exclui um produto pelo seu ID.
     *
     * @param id O ID do produto a ser excluído.
     * @return ResponseEntity indicando o sucesso ou a falha da exclusão.
     */
    @DeleteMapping("/{id}")
    private ResponseEntity<String> excluirProduto(@PathVariable("id") Long id) {
        try {
            this.produtoService.deletar(id);
            return ResponseEntity.ok().body("Produto excluído com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ocorreu um erro: " + e.getMessage());
        }
    }
    }



