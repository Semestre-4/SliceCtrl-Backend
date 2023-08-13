package com.mensal.sliceCtrl.service;

import com.mensal.sliceCtrl.DTO.EnderecosDTO;
import com.mensal.sliceCtrl.DTO.SaboresDTO;
import com.mensal.sliceCtrl.entity.Ingredientes;
import com.mensal.sliceCtrl.entity.Sabores;
import com.mensal.sliceCtrl.repository.SaboresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class SaboresService {

    @Autowired
    private SaboresRepository saboresRepository;


    private SaboresDTO toSaboresDTO (Sabores sabores){
        SaboresDTO saboresDTO = new  SaboresDTO();

        saboresDTO.setNomeSabor(sabores.getNomeSabor());
        saboresDTO.setDescricao(sabores.getDescricao());
        saboresDTO.setIngredientes(sabores.getIngredientes());

        saboresDTO.setCadastro(sabores.getCadastro());
        saboresDTO.setAtivo(sabores.isAtivo());
        saboresDTO.setEdicao(sabores.getEdicao());
        saboresDTO.setId(sabores.getId());

        return saboresDTO;
    }

    private Sabores toSabores (SaboresDTO saboresDTO){
        Sabores sabores = new  Sabores();

        sabores.setNomeSabor(saboresDTO.getNomeSabor());
        sabores.setDescricao(saboresDTO.getDescricao());
        sabores.setIngredientes(saboresDTO.getIngredientes());

        sabores.setCadastro(saboresDTO.getCadastro());
        sabores.setAtivo(saboresDTO.isAtivo());
        sabores.setEdicao(saboresDTO.getEdicao());
        sabores.setId(saboresDTO.getId());
        return sabores;
    }

    public List<SaboresDTO> getAll(){
        List<SaboresDTO> saboresDTO = saboresRepository.findAll().stream().map(this::toSaboresDTO).toList();
        return saboresDTO;
    }

    public SaboresDTO getByNome(String nomeSabor){
        Sabores sabores = this.saboresRepository.findByNome(nomeSabor);
        SaboresDTO saboresDTO = toSaboresDTO(sabores);
        return saboresDTO;
    }

    public SaboresDTO getById(Long id){
        Sabores sabores = this.saboresRepository.findById(id).orElse(null);
        SaboresDTO saboresDTO = toSaboresDTO(sabores);
        return  saboresDTO;
    }

    public ResponseEntity<String> cadastrar(SaboresDTO saboresDTO){
        Assert.hasText(saboresDTO.getNomeSabor(), "O nome deve ser informado corretamente!");
        Assert.notNull(saboresDTO.getIngredientes(), "Não informado ingredientes");

        Sabores sabores = toSabores(saboresDTO);
        this.saboresRepository.save(sabores);
        return ResponseEntity.ok().body("Cadastrado com sucesso!");
    }

    public ResponseEntity<String> editar(SaboresDTO saboresDTO){
        Sabores sabores = toSabores(saboresDTO);
        Assert.notNull(saboresDTO.getIngredientes(), "Não informado ingredientes");

        this.saboresRepository.save(sabores);
        return ResponseEntity.ok().body("Editado com sucesso");
    }

    public ResponseEntity<String> deletar(Long id){

        final Sabores sabores = this.saboresRepository.findById(id).orElse(null);
        Assert.notNull(sabores, "Sabor inexistente!");

        this.saboresRepository.delete(sabores);
        return ResponseEntity.ok().body("Deletado com sucesso!");
    }

}
