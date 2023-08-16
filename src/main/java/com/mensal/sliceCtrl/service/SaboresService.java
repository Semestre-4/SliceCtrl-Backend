package com.mensal.sliceCtrl.service;

import com.mensal.sliceCtrl.DTO.SaboresDTO;
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

    private SaboresRepository saboresRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public SaboresService(SaboresRepository saboresRepository,
                          ModelMapper modelMapper) {
        this.saboresRepository = saboresRepository;
        this.modelMapper = modelMapper;
    }

    private SaboresDTO toSaboresDTO (Sabores sabores){
        return modelMapper.map(sabores, SaboresDTO.class);
    }

    private Sabores toSabores (SaboresDTO saboresDTO){
        return modelMapper.map(saboresDTO, Sabores.class);
    }

    public List<SaboresDTO> getAll(){
        return saboresRepository.findAll().stream().map(this::toSaboresDTO).toList();
    }

    public SaboresDTO getByNome(String nomeSabor){
        Sabores sabores = this.saboresRepository.findByNome(nomeSabor);
        return toSaboresDTO(sabores);
    }

    public SaboresDTO getById(Long id){
        Sabores sabores = this.saboresRepository.findById(id).orElse(null);
        return toSaboresDTO(sabores);
    }

    public Sabores cadastrar(SaboresDTO saboresDTO){
        Assert.hasText(saboresDTO.getNomeSabor(), "O nome deve ser informado corretamente!");
        Assert.notNull(saboresDTO.getIngredientes(), "Não informado ingredientes");

        Sabores sabores = toSabores(saboresDTO);
        return this.saboresRepository.save(sabores);
    }

    public Sabores editar(SaboresDTO saboresDTO){
        Sabores sabores = toSabores(saboresDTO);
        Assert.notNull(saboresDTO.getIngredientes(), "Não informado ingredientes");

        return this.saboresRepository.save(sabores);
    }

    public void deletar(Long id){

        final Sabores sabores = this.saboresRepository.findById(id).orElse(null);
        Assert.notNull(sabores, "Sabor inexistente!");

        this.saboresRepository.delete(sabores);
    }

}
