package com.mensal.sliceCtrl.service;

import com.mensal.sliceCtrl.DTO.ClientesDTO;
import com.mensal.sliceCtrl.DTO.EnderecosDTO;
import com.mensal.sliceCtrl.DTO.PedidosDTO;
import com.mensal.sliceCtrl.entity.Clientes;
import com.mensal.sliceCtrl.entity.Enderecos;
import com.mensal.sliceCtrl.entity.Pedidos;
import com.mensal.sliceCtrl.repository.ClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public ClientesDTO findById(Long id) {
        try {
            Clientes clientesEncontrado = clienteRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com o ID: " + id));
            return toClienteDTO(clientesEncontrado);
        } catch (EntityNotFoundException ex) {
            throw new RuntimeException("Ocorreu um erro ao tentar recuperar o cliente.", ex);
        }
    }


    public List<ClientesDTO> findByNome(String nome) {
        return clienteRepository.findByNome(nome).stream().map(this::toClienteDTO).toList();
    }

    public ClientesDTO findByCPF(String cpf) {
        return toClienteDTO(clienteRepository.findByCpf(cpf));
    }

    public List<ClientesDTO> findAll() {
        return clienteRepository.findAll().stream().map(this::toClienteDTO).toList();
    }

    public Clientes createCliente(ClientesDTO clientesDTO) {
        Clientes clientes = toCliente(clientesDTO);
        return  clienteRepository.save(clientes);
    }

    public Clientes updateCliente(Long id, ClientesDTO clientesDTO) {
        Clientes existingClientes = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente com ID = " + id + " nao encontrado"));


        Clientes clientes = toCliente(clientesDTO);

        return clienteRepository.save(clientes);
    }

    public void deleteCliente(Long id) {
        Clientes clientesToDelete = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente com ID = : " + id + "nao encontrado"));

        clienteRepository.delete(clientesToDelete);
    }

    private Enderecos toEnderecos(EnderecosDTO enderecoDTO) {
        return modelMapper.map(enderecoDTO, Enderecos.class);
    }

    private Pedidos toPedido(PedidosDTO pedidosDTO) {
        return modelMapper.map(pedidosDTO, Pedidos.class);
    }

    public ClientesDTO toClienteDTO(Clientes clientes) {
        return modelMapper.map(clientes, ClientesDTO.class);
    }

    public Clientes toCliente(ClientesDTO clientesDTO) {
        return modelMapper.map(clientesDTO, Clientes.class);
    }


}
