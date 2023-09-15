package com.mensal.slicectrl.service;

import com.mensal.slicectrl.dto.IngredientesDTO;
import com.mensal.slicectrl.entity.Ingredientes;
import com.mensal.slicectrl.repository.IngredienteRepository;
import jakarta.transaction.Transactional;
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

    @Transactional
    public Ingredientes cadastrar(IngredientesDTO ingredientesDTO) {
        Ingredientes ingredientesBanco = this.ingredienteRepository.findByNome(ingredientesDTO.getNomeIngrediente());
        Assert.isTrue(ingredientesBanco == null, "Já possui um ingrediente com esse nome!");

        Ingredientes ingredientes = toIngredientes(ingredientesDTO);
        return this.ingredienteRepository.save(ingredientes);
    }

    @Transactional
    public Ingredientes editar(IngredientesDTO ingredientesDTO, Long id) {
        Ingredientes ingredientesBanco = this.ingredienteRepository.findByNome(ingredientesDTO.getNomeIngrediente());
        final Ingredientes ingredientesBD = this.ingredienteRepository.findById(id).orElse(null);

        Assert.notNull(ingredientesBD, "Endereco inexistente!");
        Assert.isTrue(ingredientesBD.getId().equals(ingredientesDTO.getId()),
                "Ingrediente informado não é o mesmo Ingrediente a ser atualizado");

        if (ingredientesBanco != null && !ingredientesDTO.getId().equals(ingredientesBanco.getId())) {
            throw new IllegalArgumentException("Já possui um ingrediente com esse nome!");
        }

        Ingredientes ingredientes = toIngredientes(ingredientesDTO);
        return this.ingredienteRepository.save(ingredientes);
    }


    @Transactional
    public void delete(final Long id) {
        final Ingredientes ingredientes = this.ingredienteRepository.findById(id).orElse(null);
        Assert.notNull(ingredientes, "Ingrediente inexiste!");


        if (ingredientes != null) {
            if (!ingredientes.getSabores().isEmpty()) {
                throw new IllegalArgumentException("Não é possível excluir o ingrediente devido às relações com sabores existentes.");
            } else {
                this.ingredienteRepository.delete(ingredientes);
            }
            }

    }

}

