package com.mensal.sliceCtrl.service;

import com.mensal.sliceCtrl.DTO.PagamentosDTO;
import com.mensal.sliceCtrl.entity.Clientes;
import com.mensal.sliceCtrl.entity.Pagamentos;
import com.mensal.sliceCtrl.entity.enums.FormasDePagamento;
import com.mensal.sliceCtrl.repository.ClienteRepository;
import com.mensal.sliceCtrl.repository.PagamentoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PagamentoService(PagamentoRepository pagamentoRepository, ModelMapper modelMapper) {
        this.pagamentoRepository = pagamentoRepository;
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
        return pagamentoRepository.findPaymentsByFormaDePagamento(formaDePagamento).stream().map(this::toPagamentosDTO).toList();
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
        Pagamentos pagamentos = toPagamentos(pagamentosDTO);
        return pagamentoRepository.save(pagamentos);
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
