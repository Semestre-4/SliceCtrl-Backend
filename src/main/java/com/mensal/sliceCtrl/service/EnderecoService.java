package com.mensal.sliceCtrl.service;

import com.mensal.sliceCtrl.DTO.EnderecosDTO;
import com.mensal.sliceCtrl.DTO.IngredientesDTO;
import com.mensal.sliceCtrl.entity.Clientes;
import com.mensal.sliceCtrl.entity.Enderecos;
import com.mensal.sliceCtrl.entity.Ingredientes;
import com.mensal.sliceCtrl.repository.ClienteRepository;
import com.mensal.sliceCtrl.repository.EnderecoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public EnderecoService(EnderecoRepository enderecoRepository,
                          ModelMapper modelMapper) {
        this.enderecoRepository = enderecoRepository;
        this.modelMapper = modelMapper;
    }

    public EnderecosDTO toEnderecosDTO (Enderecos enderecos){
        return modelMapper.map(enderecos, EnderecosDTO.class);
    }

    public Enderecos toEnderecos(EnderecosDTO enderecosDTO){
        return modelMapper.map(enderecosDTO, Enderecos.class);
    }

    public List<EnderecosDTO> getAll(){
        return enderecoRepository.findAll().stream().map(this::toEnderecosDTO).toList();
    }

    public EnderecosDTO getById(Long id){
        Enderecos enderecos = this.enderecoRepository.findById(id).orElse(null);
        return toEnderecosDTO(enderecos);
    }

    public List<EnderecosDTO> getByCep(String cep){
        return enderecoRepository.findByCep(cep).stream().map(this::toEnderecosDTO).toList();
    }

    public Enderecos cadastrar(EnderecosDTO enderecosDTO){
        Enderecos enderecos = toEnderecos(enderecosDTO);
        return this.enderecoRepository.save(enderecos);
    }

    public Enderecos editar(EnderecosDTO enderecosDTO, Long id){
        final Enderecos enderecosBanco = this.enderecoRepository.findById(id).orElse(null);
        Assert.notNull(enderecosBanco, "Endereco inexistente!");
        Assert.isTrue(enderecosBanco.getId().equals(enderecosDTO.getId()),
                "Endereco informado não é o mesmo endereco a ser atualizado");

        Enderecos enderecos = toEnderecos(enderecosDTO);
        return this.enderecoRepository.save(enderecos);
    }

    public void delete(final Long id) {
        final Enderecos enderecos = this.enderecoRepository.findById(id).orElse(null);
        Assert.notNull(enderecos, "Endereco inexistente!");

        if (!enderecos.getClientes().isEmpty()) {
            throw new IllegalArgumentException("Não é possível excluir o endereco devido à relação com clientes existente.");
        }else{
            enderecoRepository.delete(enderecos);
        }
    }

    }
