package com.mensal.sliceCtrl.repository;

import com.mensal.sliceCtrl.entity.Pagamentos;
import com.mensal.sliceCtrl.entity.Pizzas;
import com.mensal.sliceCtrl.entity.enums.FormasDePagamento;
import com.mensal.sliceCtrl.entity.enums.Tamanho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Arrays;
import java.util.List;

public interface PagamentoRepository extends JpaRepository<Pagamentos,Long> {
    @Query("SELECT p FROM Pagamentos p WHERE p.formasDePagamento = :formasDePagamento")
    List<Pagamentos> findPaymentsByFormaDePagamento(@Param("formasDePagamento") FormasDePagamento formasDePagamento);

    @Query("SELECT p FROM Pagamentos p WHERE p.isPago = true")
    List<Pagamentos> findByPago();

    @Query("SELECT p FROM Pagamentos p WHERE p.isPago = false")
    List<Pagamentos> findByNaoPago();

}
