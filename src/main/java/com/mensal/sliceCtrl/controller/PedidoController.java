package com.mensal.sliceCtrl.controller;

import com.mensal.sliceCtrl.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/pedido")
public class PedidoController {

    @Autowired
    public PedidoService pedidoService;

    //TODO : CREATE PEDIDO | GET BY ID | EDIT PEDIDO | DELETE PEDIDO | GET ALL | ADD PRODUCT (POST) | ADD PIZZA (POST)
    //TODO : UPDATE PEDIDO STATUS | PROCESS PAYMENT


}
