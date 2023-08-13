package com.mensal.sliceCtrl.service;

import com.mensal.sliceCtrl.DTO.ClienteDTO;
import com.mensal.sliceCtrl.DTO.EnderecosDTO;
import com.mensal.sliceCtrl.DTO.SaboresDTO;
import com.mensal.sliceCtrl.entity.Cliente;
import com.mensal.sliceCtrl.entity.Ingredientes;
import com.mensal.sliceCtrl.entity.Sabores;
import com.mensal.sliceCtrl.repository.SaboresRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class SaboresService {

    @Autowired
    private SaboresRepository saboresRepository;

    @Autowired
    private ModelMapper modelMapper;

    private SaboresDTO toSaboresDTO (Sabores sabores){
        return modelMapper.map(sabores, SaboresDTO.class);
    }

    private Sabores toSabores (SaboresDTO saboresDTO){
        return modelMapper.map(saboresDTO, Sabores.class);
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
