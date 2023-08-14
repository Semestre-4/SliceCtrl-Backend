package com.mensal.sliceCtrl.repository;

import com.mensal.sliceCtrl.entity.Pedidos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedidos,Long> {
}
