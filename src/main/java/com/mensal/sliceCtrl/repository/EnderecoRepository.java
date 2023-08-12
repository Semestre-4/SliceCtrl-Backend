package com.mensal.sliceCtrl.repository;

import com.mensal.sliceCtrl.entity.Enderecos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository  extends JpaRepository<Enderecos, Long> {



}
