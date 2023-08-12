package com.mensal.sliceCtrl.service;

import com.mensal.sliceCtrl.DTO.EnderecosDTO;
import com.mensal.sliceCtrl.entity.Enderecos;
import com.mensal.sliceCtrl.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;


    public EnderecosDTO toEnderecosDTO (Enderecos enderecos){
        EnderecosDTO enderecosDTO = new EnderecosDTO();

        enderecosDTO.setId(enderecos.getId());
        enderecosDTO.setAtivo(enderecos.isAtivo());
        enderecosDTO.setCadastro(enderecos.getCadastro());
        enderecosDTO.setEdicao(enderecos.getEdicao());

        enderecosDTO.setRua(enderecos.getRua());
        enderecosDTO.setNumero(enderecos.getNumero());
        enderecosDTO.setBairro(enderecos.getBairro());
        enderecosDTO.setCidade(enderecos.getCidade());
        enderecosDTO.setEstado(enderecos.getEstado());
        enderecosDTO.setPais(enderecos.getPais());
        enderecosDTO.setComplemento(enderecos.getComplemento());
        enderecosDTO.setCep(enderecos.getCep());

        return  enderecosDTO;
    }

    public Enderecos toEnderecos(EnderecosDTO enderecosDTO){

        Enderecos enderecos = new Enderecos();

        enderecos.setAtivo(enderecosDTO.isAtivo());
        enderecos.setCadastro(enderecosDTO.getCadastro());
        enderecos.setEdicao(enderecosDTO.getEdicao());

        enderecos.setRua(enderecosDTO.getRua());
        enderecos.setNumero(enderecosDTO.getNumero());
        enderecos.setBairro(enderecosDTO.getBairro());
        enderecos.setCidade(enderecosDTO.getCidade());
        enderecos.setEstado(enderecosDTO.getEstado());
        enderecos.setPais(enderecosDTO.getPais());
        enderecos.setComplemento(enderecosDTO.getComplemento());
        enderecos.setCep(enderecosDTO.getCep());

        return enderecos;
    }

    public List<EnderecosDTO> getAll(){
        List<EnderecosDTO> enderecosDTOS = enderecoRepository.findAll().stream().map(this::toEnderecosDTO).toList();

    return enderecosDTOS;
    }

}
