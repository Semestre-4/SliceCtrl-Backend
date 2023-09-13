package com.mensal.slicectrl.repository;

import com.mensal.slicectrl.entity.Clientes;
import com.mensal.slicectrl.entity.Pedidos;
import com.mensal.slicectrl.entity.enums.FormaDeEntrega;
import com.mensal.slicectrl.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedidos, Long> {

    @Query("SELECT p FROM Pedidos p WHERE p.status = :status")
    List<Pedidos> findByStatus(@Param("status") Status status);

    @Query("SELECT p FROM Pedidos p WHERE p.formaDeEntrega = :formaDeEntrega")
    List<Pedidos> findByformaDeEntrega(FormaDeEntrega formaDeEntrega);

    @Query("SELECT p FROM Pedidos p WHERE p.cliente.id = :id")
    List<Pedidos> findByCliente(@Param("id") Long id);

    @Query("SELECT p FROM Pedidos p WHERE p.funcionario.id = :id")
    List<Pedidos> findByFunc(@Param("id") Long id);

    @Query("SELECT p FROM Pedidos p WHERE p.status = :PENDENTE")
    List<Pedidos> findOrdersWithPendingPayments();

    @Query("SELECT p FROM Pedidos p WHERE p.cliente = :cliente AND p.status = :status")
    List<Pedidos> findByClienteAndStatus(@Param("cliente") Clientes cliente, @Param("status") Status status);}
