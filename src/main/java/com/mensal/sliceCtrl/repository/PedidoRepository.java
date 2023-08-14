package com.mensal.sliceCtrl.repository;

import com.mensal.sliceCtrl.entity.Pedidos;
import com.mensal.sliceCtrl.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedidos, Long> {

    @Query("SELECT p FROM Pedidos p WHERE p.status = :status")
    List<Pedidos> findByStatus(@Param("status") Status status);

    @Query("SELECT p FROM Pedidos p WHERE p.cliente.id = :id")
    List<Pedidos> findByCliente(@Param("id") Long id);

    @Query("SELECT p FROM Pedidos p WHERE p.funcionario.id = :id")
    List<Pedidos> findByFunc(@Param("id") Long id);

    @Query("SELECT p FROM Pedidos p WHERE p.forEntrega = true")
    List<Pedidos> findByForEntrega();

    @Query("SELECT p FROM Pedidos p WHERE p.forTakeaway = true")
    List<Pedidos> findByForTakeaway();

    @Query("SELECT p FROM Pedidos p WHERE p.forDineIn = true")
    List<Pedidos> findByForDineIn();

    @Query("SELECT p FROM Pedidos p WHERE p.status = :PENDENTE")
    List<Pedidos> findOrdersWithPendingPayments();
}
