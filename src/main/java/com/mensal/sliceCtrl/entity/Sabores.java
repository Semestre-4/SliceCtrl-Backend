package com.mensal.sliceCtrl.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "sabores", schema = "public")
public class Sabores extends AbstractEntity{

    @NotBlank(message = "É obrigatorio informar o nome do sabor")
    @NotBlank(message = "É obrigatorio informar o nome do sabor")
    @Size(min = 2, max = 50, message = "O nome deve ter entre 2 e 50 caracteres")
    @Column(name = "nome_sabor", nullable = false, unique = true)
    private String nomeSabor;

    @Column(name = "descricao_sabor", nullable = true)
    private String descricao;

    @NotNull(message = "É necessario informar o ingrediente")
    @JoinColumn(name = "ingrediente", nullable = false)
    @ManyToMany
    private List<Ingredientes> ingredientes;
}
