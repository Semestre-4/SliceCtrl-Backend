package com.mensal.sliceCtrl.service;

import com.mensal.sliceCtrl.DTO.IngredientesDTO;
import com.mensal.sliceCtrl.DTO.ProdutosDTO;
import com.mensal.sliceCtrl.entity.Enderecos;
import com.mensal.sliceCtrl.entity.Produtos;
import com.mensal.sliceCtrl.entity.enums.Categorias;
import com.mensal.sliceCtrl.repository.ProdutoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private ModelMapper modelMapper;


    public Produtos toProdutos(ProdutosDTO produtosDTO) {
        return modelMapper.map(produtosDTO, Produtos.class);
    }

    public ProdutosDTO toProdutosDTO(Produtos produtos) {
        return modelMapper.map(produtos, ProdutosDTO.class);
    }

    public List<ProdutosDTO> getAll() {
        List<ProdutosDTO> produtosDTO = produtoRepository.findAll().stream().map(this::toProdutosDTO).toList();
        return produtosDTO;
    }

    public ProdutosDTO getByNome(String nomeProduto) {
        Produtos produtos = this.produtoRepository.findByNome(nomeProduto);
        ProdutosDTO produtosDTO = toProdutosDTO(produtos);
        return produtosDTO;
    }

    public ProdutosDTO getById(Long id) {
        Produtos produtos = this.produtoRepository.findById(id).orElse(null);
        ProdutosDTO produtosDTO = toProdutosDTO(produtos);
        return produtosDTO;
    }

    public ProdutosDTO getByCategoria(String categoria) {
        Produtos produtos = this.produtoRepository.findByCategoria(categoria);
        ProdutosDTO produtosDTO = toProdutosDTO(produtos);
        return produtosDTO;
    }


    public List<ProdutosDTO> getByDisponivel(Boolean disponivel) {
        List<ProdutosDTO> produtosDTO = produtoRepository.findByDisponivel(disponivel).stream().map(this::toProdutosDTO).toList();

        return produtosDTO;
    }

    public ResponseEntity<String> cadastrar(ProdutosDTO produtosDTO) {
        Produtos produtos = toProdutos(produtosDTO);
        this.produtoRepository.save(produtos);
        return ResponseEntity.ok().body("Cadastrado com sucesso!");
    }

    public ResponseEntity<String> editar(ProdutosDTO produtosDTO){
        Produtos produtos = toProdutos(produtosDTO);
        this.produtoRepository.save(produtos);
        return ResponseEntity.ok().body("Editado com sucesso!");
    }

    public ResponseEntity<String> deletar(Long id){
        this.produtoRepository.deleteById(id);
        return ResponseEntity.ok().body("Deletado com sucesso!");
    }
}
