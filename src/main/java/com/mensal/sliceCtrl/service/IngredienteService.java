package com.mensal.sliceCtrl.service;

import com.mensal.sliceCtrl.DTO.EnderecosDTO;
import com.mensal.sliceCtrl.DTO.IngredientesDTO;
import com.mensal.sliceCtrl.entity.Enderecos;
import com.mensal.sliceCtrl.entity.Ingredientes;
import com.mensal.sliceCtrl.repository.IngredienteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class IngredienteService {

    @Autowired
    private IngredienteRepository ingredienteRepository;

    @Autowired
    private ModelMapper modelMapper;


    public IngredientesDTO toIngredientesDTO (Ingredientes ingredientes){
        return modelMapper.map(ingredientes, IngredientesDTO.class);
    }

    public Ingredientes toIngredientes(IngredientesDTO ingredientesDTO){
        return modelMapper.map(ingredientesDTO, Ingredientes.class);
    }

    public IngredientesDTO getById(Long id){
        Ingredientes ingredientes = this.ingredienteRepository.findById(id).orElse(null);
        IngredientesDTO ingredientesDTO = toIngredientesDTO(ingredientes);
        return ingredientesDTO;
    }

    public List<IngredientesDTO> getAll(){
        List<IngredientesDTO> ingredientesDTO = ingredienteRepository.findAll().stream().map(this::toIngredientesDTO).toList();

        return  ingredientesDTO;
    }

    public IngredientesDTO getByNome(String nome){
        Ingredientes ingredientes = this.ingredienteRepository.findByNome(nome);
        IngredientesDTO ingredientesDTO = toIngredientesDTO(ingredientes);

        return ingredientesDTO;
    }


    public ResponseEntity<String> cadastrar(IngredientesDTO ingredientesDTO){
        Ingredientes ingredientes = toIngredientes(ingredientesDTO);

        this.ingredienteRepository.save(ingredientes);

        return ResponseEntity.ok().body("Cadastrado com sucesso");
    }

    public ResponseEntity<String> editar(IngredientesDTO ingredientesDTO, Long id){

        final Ingredientes ingredientesBD = this.ingredienteRepository.findById(id).orElse(null);
        Assert.notNull(ingredientesBD, "Endereco inexistente!");
        Assert.isTrue(ingredientesBD.getId().equals(ingredientesDTO.getId()), "Ingrediente informado não é o mesmo Ingrediente a ser atualizado");

        Ingredientes ingredientes = toIngredientes(ingredientesDTO);

        this.ingredienteRepository.save(ingredientes);

        return ResponseEntity.ok().body("Editado com sucesso");
    }

    public String delete(final Long id) {

        final Ingredientes ingredientes = this.ingredienteRepository.findById(id).orElse(null);
        Assert.notNull(ingredientes, "Endereco inexistente!");
        this.ingredienteRepository.delete(ingredientes);

        return "Deletado com sucesso!";

    }

}
