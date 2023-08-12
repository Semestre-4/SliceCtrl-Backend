package com.mensal.sliceCtrl.service;

import com.mensal.sliceCtrl.DTO.EnderecosDTO;
import com.mensal.sliceCtrl.entity.Enderecos;
import com.mensal.sliceCtrl.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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
