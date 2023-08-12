package com.mensal.sliceCtrl.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDateTime;

@MappedSuperclass
@Table(name = "AbstractEntity", schema = "public")
public class AbstractEntity {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Getter @Setter
    @Column(name = "data_Cadastro", nullable = false)
    private LocalDateTime cadastro;

    @Getter @Setter
    @Column(name = "data_edicao")
    private LocalDateTime edicao;

    @Getter @Setter
    @Column(name = "ativo", nullable = false)
    private boolean ativo;

    @PrePersist
    private void prePersist() {
        this.cadastro = LocalDateTime.now();
        this.ativo = true;
    }

    @PreUpdate
    private void preUpData(){
        this.edicao = LocalDateTime.now();

    }


}
