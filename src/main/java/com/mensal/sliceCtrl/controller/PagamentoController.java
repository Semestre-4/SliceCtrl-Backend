package com.mensal.sliceCtrl.controller;

import com.mensal.sliceCtrl.DTO.PagamentosDTO;
import com.mensal.sliceCtrl.entity.Pagamentos;
import com.mensal.sliceCtrl.entity.enums.FormasDePagamento;
import com.mensal.sliceCtrl.service.PagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagamento")
public class PagamentoController {

    private final PagamentoService pagamentoService;

    @Autowired
    public PagamentoController(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getPagamentoById(@PathVariable("id") Long id) {
        PagamentosDTO pagamentosDTO = pagamentoService.findById(id);
        if (pagamentosDTO != null) {
            return ResponseEntity.ok(pagamentosDTO);
        } else {
            return ResponseEntity.badRequest().body("Payment ID not found");
        }
    }

    @GetMapping("/{formasDePagamento}")
    public ResponseEntity<List<PagamentosDTO>> getPaymentsByFormaDePagamento(
            @RequestParam("formasDePagamento") FormasDePagamento formasDePagamento) {
        List<PagamentosDTO> payments = pagamentoService.findPaymentsByFormaDePagamento(formasDePagamento);
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    @GetMapping("/pago")
    public ResponseEntity<List<PagamentosDTO>> getByPago() {
        List<PagamentosDTO> payments = pagamentoService.findByPago();
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    @GetMapping("/naoPago")
    public ResponseEntity<List<PagamentosDTO>> getByNaoPago() {
        List<PagamentosDTO> payments = pagamentoService.findByNaoPago();
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PagamentosDTO>> getAllPagamentos() {
        List<PagamentosDTO> pagamentosDTOS = pagamentoService.findAll();
        return ResponseEntity.ok(pagamentosDTOS);
    }

    @PostMapping
    public ResponseEntity<?> realizarPagamento(@RequestBody @Validated PagamentosDTO pagamentosDTO) {
        try {
            Pagamentos realizarPagamento = pagamentoService.realizarPagamento(pagamentosDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(realizarPagamento);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirPagamento(@PathVariable Long id) {
        try {
            pagamentoService.deletePagamento(id);
            return ResponseEntity.ok().body("Payment successfully deleted!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
