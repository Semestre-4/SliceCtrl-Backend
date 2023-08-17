package com.mensal.sliceCtrl.controller;

import com.mensal.sliceCtrl.entity.PedidoPizza;
import com.mensal.sliceCtrl.entity.PedidoProduto;
import com.mensal.sliceCtrl.repository.PedidoPizzaRepository;
import com.mensal.sliceCtrl.repository.PedidoProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/pedido/pizza")
public class PedidoPizzaController {

    @Autowired
    public PedidoPizzaRepository pedidoPizzaRepository;

    @PostMapping
    public ResponseEntity<String> createPedidoProduto(@RequestBody PedidoPizza pedidoPizza) {
        pedidoPizzaRepository.save(pedidoPizza);
        return ResponseEntity.ok("PedidoPizza created successfully.");
    }

}
