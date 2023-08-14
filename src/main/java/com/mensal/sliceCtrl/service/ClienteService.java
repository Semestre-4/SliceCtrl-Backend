package com.mensal.sliceCtrl.service;


import com.mensal.sliceCtrl.DTO.EnderecosDTO;
import com.mensal.sliceCtrl.DTO.PedidosDTO;
import com.mensal.sliceCtrl.DTO.ClientesDTO;
import com.mensal.sliceCtrl.entity.Clientes;
import com.mensal.sliceCtrl.entity.Enderecos;
import com.mensal.sliceCtrl.entity.Pedidos;
import com.mensal.sliceCtrl.repository.ClienteRepository;
import com.mensal.sliceCtrl.repository.EnderecoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final EnderecoRepository enderecoRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository,
                          EnderecoRepository enderecoRepository,
                          ModelMapper modelMapper) {
        this.clienteRepository = clienteRepository;
        this.enderecoRepository = enderecoRepository;
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
        List<Enderecos> enderecosList = new ArrayList<>();

        for (EnderecosDTO enderecoDTO : clientesDTO.getEnderecos()) {
            Enderecos existingEndereco = enderecoRepository.findById(enderecoDTO.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Endereco com ID = " + enderecoDTO.getId() + " nao encontrado"));
            enderecosList.add(existingEndereco);
        }

        Clientes clientes = toCliente(clientesDTO, enderecosList);

        return clienteRepository.save(clientes);
    }

    public Clientes updateCliente(Long id, ClientesDTO clientesDTO) {
        Clientes existingClientes = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente com ID = " + id + " nao encontrado"));

        List<Enderecos> existingEnderecos = new ArrayList<>();
        for (EnderecosDTO enderecoDTO : clientesDTO.getEnderecos()) {
            Enderecos existingEndereco = enderecoRepository.findById(enderecoDTO.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Endereco com ID = " + enderecoDTO.getId() + " nao encontrado"));
            existingEnderecos.add(existingEndereco);
        }

        Clientes clientes = toCliente(clientesDTO, existingEnderecos);

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

    public Clientes toCliente(ClientesDTO clientesDTO, List<Enderecos> enderecosList) {
        Clientes clientes = modelMapper.map(clientesDTO, Clientes.class);
        clientes.setEnderecos(enderecosList);
        return clientes;
    }

}
