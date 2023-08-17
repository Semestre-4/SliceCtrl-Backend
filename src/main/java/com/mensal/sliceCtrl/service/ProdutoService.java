package com.mensal.sliceCtrl.service;

import com.mensal.sliceCtrl.DTO.IngredientesDTO;
import com.mensal.sliceCtrl.DTO.PizzasDTO;
import com.mensal.sliceCtrl.DTO.ProdutosDTO;
import com.mensal.sliceCtrl.entity.Enderecos;
import com.mensal.sliceCtrl.entity.Produtos;
import com.mensal.sliceCtrl.entity.enums.Categorias;
import com.mensal.sliceCtrl.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    private ProdutoRepository produtoRepository;
    private ModelMapper modelMapper;

    @Autowired
    public ProdutoService(ProdutoRepository produtoRepository,
                          ModelMapper modelMapper) {
        this.produtoRepository = produtoRepository;
        this.modelMapper = modelMapper;
    }


    public Produtos toProdutos(ProdutosDTO produtosDTO) {
        return modelMapper.map(produtosDTO, Produtos.class);
    }

    public ProdutosDTO toProdutosDTO(Produtos produtos) {
        return modelMapper.map(produtos, ProdutosDTO.class);
    }

    public List<ProdutosDTO> getAll() {
        return produtoRepository.findAll().stream().map(this::toProdutosDTO).toList();
    }

    public ProdutosDTO getByNome(String nomeProduto) {
        Produtos produtos = this.produtoRepository.findByNome(nomeProduto);
        return toProdutosDTO(produtos);
    }

    public ProdutosDTO getById(Long id) {
        Produtos produtos = this.produtoRepository.findById(id).orElse(null);
        return toProdutosDTO(produtos);
    }

    public List<ProdutosDTO> findByCategoria(Categorias categoria) {
        return this.produtoRepository.findByCategoria(categoria).stream().map(this::toProdutosDTO).toList();
    }

    @Transactional
    public Produtos cadastrar(ProdutosDTO produtosDTO) {
        Produtos produtos = toProdutos(produtosDTO);
        return this.produtoRepository.save(produtos);
    }

    @Transactional
    public Produtos editar(ProdutosDTO produtosDTO){
        Produtos produtos = toProdutos(produtosDTO);
        return this.produtoRepository.save(produtos);
    }

    @Transactional
    public void deletar(Long id){
        Produtos produtos = this.produtoRepository.findById(id).orElse(null);


        if (produtos != null) {
            if (!produtos.getPedidos().isEmpty()) {
                throw new IllegalArgumentException("Não é possível excluir o produto devido à relação com pedidos existentes.");
            } else {
                this.produtoRepository.delete(produtos);
            }
    }}

    public List<ProdutosDTO> findByDisponivel() {
        return produtoRepository.findByDisponivelTrue().stream().map(this::toProdutosDTO).toList();
    }

}
