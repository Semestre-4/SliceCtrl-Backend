package com.mensal.sliceCtrl.service;

import com.mensal.sliceCtrl.DTO.ClienteDTO;
import com.mensal.sliceCtrl.DTO.EnderecosDTO;
import com.mensal.sliceCtrl.DTO.IngredientesDTO;
import com.mensal.sliceCtrl.DTO.PedidoDTO;
import com.mensal.sliceCtrl.entity.Cliente;
import com.mensal.sliceCtrl.entity.Enderecos;
import com.mensal.sliceCtrl.entity.Ingredientes;
import com.mensal.sliceCtrl.entity.Pedido;
import com.mensal.sliceCtrl.repository.ClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository, ModelMapper modelMapper) {
        this.clienteRepository = clienteRepository;
        this.modelMapper = modelMapper;
    }

    public ClienteDTO findById(Long id) {
        try {
            Cliente clienteEncontrado = clienteRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com o ID: " + id));
            return toClienteDTO(clienteEncontrado);
        } catch (EntityNotFoundException ex) {
            throw new RuntimeException("Ocorreu um erro ao tentar recuperar o cliente.", ex);
        }
    }


    public List<ClienteDTO> findByNome(String nome) {
        return clienteRepository.findByNome(nome).stream().map(this::toClienteDTO).toList();
    }

    public ClienteDTO findByCPF(String cpf) {
        return toClienteDTO(clienteRepository.findByCpf(cpf));
    }

    public List<ClienteDTO> findAll() {
        return clienteRepository.findAll().stream().map(this::toClienteDTO).toList();
    }

    public Cliente createCliente(ClienteDTO clienteDTO) {
        Cliente cliente = toCliente(clienteDTO);
        return  clienteRepository.save(cliente);
    }

    public Cliente updateCliente(Long id, ClienteDTO clienteDTO) {
        Cliente existingCliente = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente com ID = " + id + " nao encontrado"));


        Cliente cliente = toCliente(clienteDTO);

        return clienteRepository.save(cliente);
    }

    public void deleteCliente(Long id) {
        Cliente clienteToDelete = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente com ID = : " + id + "nao encontrado"));

        clienteRepository.delete(clienteToDelete);
    }

    private Enderecos toEnderecos(EnderecosDTO enderecoDTO) {
        return modelMapper.map(enderecoDTO, Enderecos.class);
    }

    private Pedido toPedido(PedidoDTO pedidoDTO) {
        return modelMapper.map(pedidoDTO, Pedido.class);
    }

    public ClienteDTO toClienteDTO(Cliente cliente) {
        return modelMapper.map(cliente, ClienteDTO.class);
    }

    public Cliente toCliente(ClienteDTO clienteDTO) {
        return modelMapper.map(clienteDTO, Cliente.class);
    }


}
