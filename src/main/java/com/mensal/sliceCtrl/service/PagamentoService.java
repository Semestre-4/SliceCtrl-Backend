package com.mensal.sliceCtrl.service;

import com.mensal.sliceCtrl.DTO.PagamentosDTO;
import com.mensal.sliceCtrl.entity.Pagamentos;
import com.mensal.sliceCtrl.entity.Pedidos;
import com.mensal.sliceCtrl.entity.Pizzas;
import com.mensal.sliceCtrl.entity.Produtos;
import com.mensal.sliceCtrl.entity.enums.FormasDePagamento;
import com.mensal.sliceCtrl.repository.PagamentoRepository;
import com.mensal.sliceCtrl.repository.PedidoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;
    private final PedidoRepository pedidoRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PagamentoService(PagamentoRepository pagamentoRepository,
                            PedidoRepository pedidoRepository,
                            ModelMapper modelMapper) {
        this.pagamentoRepository = pagamentoRepository;
        this.pedidoRepository = pedidoRepository;
        this.modelMapper = modelMapper;
    }

    public PagamentosDTO findById(Long id) {
        try {
            Pagamentos pagamentosEncontrado = pagamentoRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Pagamento não encontrado com o ID: " + id));
            return toPagamentosDTO(pagamentosEncontrado);
        } catch (EntityNotFoundException ex) {
            throw new RuntimeException("Ocorreu um erro ao tentar recuperar o pagamento.", ex);
        }
    }

    public List<PagamentosDTO> findPaymentsByFormaDePagamento(FormasDePagamento formaDePagamento) {
        return pagamentoRepository.findPaymentsByFormaDePagamento(formaDePagamento)
                .stream().map(this::toPagamentosDTO).toList();
    }

    public List<PagamentosDTO> findByPago() {
        return pagamentoRepository.findByPago().stream().map(this::toPagamentosDTO).toList();
    }

    public List<PagamentosDTO> findByNaoPago() {
        return pagamentoRepository.findByNaoPago().stream().map(this::toPagamentosDTO).toList();
    }

    public List<PagamentosDTO> findAll() {
        return pagamentoRepository.findAll().stream().map(this::toPagamentosDTO).toList();
    }

    public Pagamentos realizarPagamento(PagamentosDTO pagamentosDTO) {
        try {

            Pedidos pedido = getExistingPedido(pagamentosDTO.getPedidoId());

            BigDecimal totalAmount = calculateTotalAmount(pedido);
            pagamentosDTO.setPago(true);
            pedido.setValorTotal(totalAmount);

            Pagamentos pagamentos = toPagamentos(pagamentosDTO);
            return pagamentoRepository.save(pagamentos);
        } catch (Exception e) {
            throw new IllegalArgumentException("Pedido não encontrado", e);
        }
    }

    private Pedidos getExistingPedido(long pedidoId) {
        return pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado"));
    }

    private BigDecimal calculateTotalAmount(Pedidos pedido) {
        BigDecimal productsTotal = calculateProductsTotal(pedido);
        BigDecimal pizzasTotal = calculatePizzasTotal(pedido);
        BigDecimal deliveryTotal = pedido.isForEntrega() ? pedido.getValorEntrega() : BigDecimal.ZERO;

        pedido.setValorEntrega(deliveryTotal);
        pedido.setValorPedido(productsTotal.add(pizzasTotal));
        return productsTotal.add(pizzasTotal).add(deliveryTotal);
    }

    private BigDecimal calculateProductsTotal(Pedidos pedido) {
        return pedido.getProdutos().stream()
                .map(produtos -> BigDecimal.valueOf(produtos.getPreco()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculatePizzasTotal(Pedidos pedido) {
        return pedido.getPizzas().stream()
                .map(pizzas -> BigDecimal.valueOf(pizzas.getPreco()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


    public void deletePagamento(Long id) {
        Pagamentos pagamentosToDelete = pagamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pagamento com ID = : " + id + "nao encontrado"));
        pagamentoRepository.delete(pagamentosToDelete);
    }

    public PagamentosDTO toPagamentosDTO(Pagamentos pagamentos) {
        return modelMapper.map(pagamentos, PagamentosDTO.class);
    }

    public Pagamentos toPagamentos(PagamentosDTO pagamentosDTO) {
        return modelMapper.map(pagamentosDTO, Pagamentos.class);
    }

}
