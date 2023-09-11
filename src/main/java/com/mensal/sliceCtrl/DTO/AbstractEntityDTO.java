package com.mensal.sliceCtrl.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class AbstractEntityDTO {

    private Long id;
    private LocalDateTime cadastro;
    private LocalDateTime edicao;
    private boolean ativo;

}
