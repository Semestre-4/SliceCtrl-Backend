package com.mensal.sliceCtrl.controller;

import com.mensal.sliceCtrl.entity.PedidoProduto;
import com.mensal.sliceCtrl.repository.PedidoProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/pedido/produto")
public class PedidoProdutoController {

    @Autowired
    public PedidoProdutoRepository pedidoProdutoRepository;

    @PostMapping
    public ResponseEntity<String> createPedidoProduto(@RequestBody PedidoProduto pedidoProduto) {
        pedidoProdutoRepository.save(pedidoProduto);
        return ResponseEntity.ok("PedidoProduto created successfully.");
    }
}