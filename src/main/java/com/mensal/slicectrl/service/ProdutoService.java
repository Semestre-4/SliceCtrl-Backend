package com.mensal.slicectrl.service;

import com.mensal.slicectrl.dto.ProdutosDTO;
import com.mensal.slicectrl.entity.Produtos;
import com.mensal.slicectrl.entity.enums.Categoria;
import com.mensal.slicectrl.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<ProdutosDTO> getByCategoria(Categoria categoria) {
        return this.produtoRepository.findByCategoria(categoria).stream().map(this::toProdutosDTO).toList();
    }

    @Transactional
    public Produtos cadastrar(ProdutosDTO produtosDTO) {
        Produtos produtos = toProdutos(produtosDTO);
        produtos.setDisponivel(produtos.getQtdeEstoque() != 0);
        return this.produtoRepository.save(produtos);
    }

    @Transactional
    public Produtos editar(ProdutosDTO produtosDTO){
        Produtos produtos = toProdutos(produtosDTO);
        if (produtos.getQtdeEstoque() == 0) {
            produtos.setDisponivel(false);
        }
        return this.produtoRepository.save(produtos);
    }

    @Transactional
    public void deletar(Long id){
        Produtos produtos = this.produtoRepository.findById(id).orElse(null);

        produtos.setAtivo(false);
        produtoRepository.save(produtos);

    }

    public List<ProdutosDTO> getByDisponivel() {
        return produtoRepository.findByDisponivelTrue().stream().map(this::toProdutosDTO).toList();
    }

}
