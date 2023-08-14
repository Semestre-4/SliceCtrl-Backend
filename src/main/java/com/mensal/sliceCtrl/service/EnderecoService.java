package com.mensal.sliceCtrl.service;

import com.mensal.sliceCtrl.DTO.EnderecosDTO;
import com.mensal.sliceCtrl.DTO.IngredientesDTO;
import com.mensal.sliceCtrl.entity.Enderecos;
import com.mensal.sliceCtrl.entity.Ingredientes;
import com.mensal.sliceCtrl.repository.EnderecoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private ModelMapper modelMapper;


    public EnderecosDTO toEnderecosDTO (Enderecos enderecos){
        return modelMapper.map(enderecos, EnderecosDTO.class);
    }

    public Enderecos toEnderecos(EnderecosDTO enderecosDTO){
        return modelMapper.map(enderecosDTO, Enderecos.class);
    }

    public List<EnderecosDTO> getAll(){
        List<EnderecosDTO> enderecosDTOS = enderecoRepository.findAll().stream().map(this::toEnderecosDTO).toList();

    return enderecosDTOS;
    }

    public EnderecosDTO getById(Long id){
        Enderecos enderecos = this.enderecoRepository.findById(id).orElse(null);
        EnderecosDTO enderecosDTO = toEnderecosDTO(enderecos);
        return enderecosDTO;
    }

    public List<EnderecosDTO> getByCep(String cep){
        List<EnderecosDTO> enderecosDTOS = enderecoRepository.findByCep(cep).stream().map(this::toEnderecosDTO).toList();
        return  enderecosDTOS;
    }



    public String cadastrar(EnderecosDTO enderecosDTO){
        Enderecos enderecos = toEnderecos(enderecosDTO);
        this.enderecoRepository.save(enderecos);
        return "Cadastrado com sucesso";
    }

    public Enderecos editar(EnderecosDTO enderecosDTO, Long id){

        final Enderecos enderecosBanco = this.enderecoRepository.findById(id).orElse(null);
        Assert.notNull(enderecosBanco, "Endereco inexistente!");
        Assert.isTrue(enderecosBanco.getId().equals(enderecosDTO.getId()), "Endereco informado não é o mesmo endereco a ser atualizado");

        Enderecos enderecos = toEnderecos(enderecosDTO);

        return this.enderecoRepository.save(enderecos);

    }

    public String delete(final Long id) {

        final Enderecos enderecos = this.enderecoRepository.findById(id).orElse(null);
        Assert.notNull(enderecos, "Endereco inexistente!");
        this.enderecoRepository.delete(enderecos);

        return "Deletado com sucesso!";

    }
    }
