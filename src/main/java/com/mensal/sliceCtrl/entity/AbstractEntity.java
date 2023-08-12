package com.mensal.sliceCtrl.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@MappedSuperclass
public class AbstractEntity {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Getter @Setter
    @Column(name = "data_Cadastro", nullable = false)
    private LocalDateTime cadastro;

    @Getter @Setter
    @Column(name = "data_edicao")
    private LocalDateTime edicao;

    @PrePersist
    private void prePersist() {
        this.cadastro = LocalDateTime.now();
    }

    @PreUpdate
    private void preUpData(){

        this.edicao = LocalDateTime.now();

    }






}
