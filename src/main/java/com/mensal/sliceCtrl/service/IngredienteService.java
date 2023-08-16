package com.mensal.sliceCtrl.service;

import com.mensal.sliceCtrl.DTO.IngredientesDTO;
import com.mensal.sliceCtrl.entity.Ingredientes;
import com.mensal.sliceCtrl.repository.IngredienteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class IngredienteService {

    private IngredienteRepository ingredienteRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public IngredienteService(IngredienteRepository ingredienteRepository,
                              ModelMapper modelMapper) {
        this.ingredienteRepository = ingredienteRepository;
        this.modelMapper = modelMapper;
    }


    public IngredientesDTO toIngredientesDTO(Ingredientes ingredientes) {
        return modelMapper.map(ingredientes, IngredientesDTO.class);
    }

    public Ingredientes toIngredientes(IngredientesDTO ingredientesDTO) {
        return modelMapper.map(ingredientesDTO, Ingredientes.class);
    }

    public IngredientesDTO getById(Long id) {
        Ingredientes ingredientes = this.ingredienteRepository.findById(id).orElse(null);
        return toIngredientesDTO(ingredientes);
    }

    public List<IngredientesDTO> getAll() {
        return ingredienteRepository.findAll().stream().map(this::toIngredientesDTO).toList();
    }

    public IngredientesDTO getByNome(String nome) {
        Ingredientes ingredientes = this.ingredienteRepository.findByNome(nome);
        return toIngredientesDTO(ingredientes);
    }


    public Ingredientes cadastrar(IngredientesDTO ingredientesDTO) {
        Ingredientes ingredientes = toIngredientes(ingredientesDTO);
        return this.ingredienteRepository.save(ingredientes);
    }

    public Ingredientes editar(IngredientesDTO ingredientesDTO, Long id) {

        final Ingredientes ingredientesBD = this.ingredienteRepository.findById(id).orElse(null);
        Assert.notNull(ingredientesBD, "Endereco inexistente!");
        Assert.isTrue(ingredientesBD.getId().equals(ingredientesDTO.getId()),
                "Ingrediente informado não é o mesmo Ingrediente a ser atualizado");

        Ingredientes ingredientes = toIngredientes(ingredientesDTO);
        return this.ingredienteRepository.save(ingredientes);
    }

    public void delete(final Long id) {
        final Ingredientes ingredientes = this.ingredienteRepository.findById(id).orElse(null);
        Assert.notNull(ingredientes, "Endereco inexistente!");
        this.ingredienteRepository.delete(ingredientes);
    }

}
