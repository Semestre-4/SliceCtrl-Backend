package com.mensal.sliceCtrl.controller;

import com.mensal.sliceCtrl.service.SaboresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sabores")
public class SaboresController {

    @Autowired
    private SaboresService saboresService;



}
