package com.mensal.slicectrl.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pizzaria_config")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Configuration extends AbstractEntity{
    @Column(name = "moeda")
    private String moeda;

    @Column(name = "fuso_horario-start")
    private LocalDateTime startDate;

    @Column(name = "fuso_horario-end")
    private LocalDateTime endDate;

    @Column(name = "taxa_entrega")
    private BigDecimal taxaEntrega;

}
