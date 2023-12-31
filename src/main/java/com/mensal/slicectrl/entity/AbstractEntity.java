package com.mensal.slicectrl.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@MappedSuperclass
public abstract class AbstractEntity {

    @Id
    @Getter @Setter
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
