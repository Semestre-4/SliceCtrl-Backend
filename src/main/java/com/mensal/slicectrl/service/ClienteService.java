package com.mensal.slicectrl.service;

import com.mensal.slicectrl.dto.EnderecosDTO;
import com.mensal.slicectrl.dto.ClientesDTO;
import com.mensal.slicectrl.entity.Clientes;
import com.mensal.slicectrl.entity.Enderecos;
import com.mensal.slicectrl.repository.ClienteRepository;
import com.mensal.slicectrl.repository.EnderecoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
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

    /**
     * Encontra um cliente pelo seu ID.
     *
     * @param id O ID do cliente a ser encontrado.
     * @return O DTO do cliente se encontrado.
     * @throws RuntimeException se o cliente não for encontrado.
     */
    public ClientesDTO findById(Long id) {
        try {
            Clientes clientesEncontrado = clienteRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado com o ID: " + id));
            return toClienteDTO(clientesEncontrado);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Ocorreu um erro ao tentar recuperar o cliente."+ex.getMessage(), ex);
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

    @Transactional
    public Clientes createCliente(ClientesDTO clientesDTO) {
        if (clienteRepository.existsByCpf(clientesDTO.getCpf())) {
            throw new IllegalArgumentException("Cliente with CPF " + clientesDTO.getCpf() + " already exists.");
        }

        List<Enderecos> enderecosList = new ArrayList<>();

        for (EnderecosDTO enderecoDTO : clientesDTO.getEnderecos()) {
            Enderecos endereco = modelMapper.map(enderecoDTO, Enderecos.class);
            enderecosList.add(endereco);
        }

        Clientes clientes = modelMapper.map(clientesDTO, Clientes.class);
        clientes.setEnderecos(enderecosList);

        try {
            // Make sure to check if the save operation returns a non-null result
            return clienteRepository.save(clientes);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Failed to create the client.", ex);
        }
    }

    @Transactional
    public Clientes updateCliente(Long id, ClientesDTO clientesDTO) {

        if (!id.equals(clientesDTO.getId())) {
            throw new IllegalArgumentException("O ID na URL não corresponde ao ID no corpo da requisição");
        }

        List<Enderecos> existingEnderecos = new ArrayList<>();

        for (EnderecosDTO enderecoDTO : clientesDTO.getEnderecos()) {
            existingEnderecos.add(toEnderecos(enderecoDTO));
        }

        validateEnderecoIds(clientesDTO.getEnderecos());

        Clientes clientes = toCliente(clientesDTO, existingEnderecos);
        return clienteRepository.save(clientes);
    }

    private void validateEnderecoIds(List<EnderecosDTO> enderecoDTOs) {
        for (EnderecosDTO enderecoDTO : enderecoDTOs) {
            try {
                enderecoRepository.findById(enderecoDTO.getId())
                        .orElseThrow(() -> new EntityNotFoundException("Endereco com ID = " + enderecoDTO.getId() + " nao encontrado"));
            } catch (EntityNotFoundException ex) {
                throw new EntityNotFoundException(ex);
            }
        }
    }


    @Transactional
    public void deleteCliente(Long id) {
        Clientes clientesToDelete = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente com ID = " + id + " não encontrado"));

        if (clientesToDelete.getPedidos().isEmpty() && clientesToDelete.getEnderecos().isEmpty()) {
            clienteRepository.delete(clientesToDelete);
        } else {
            desativarCliente(clientesToDelete);
        }
    }

    private void desativarCliente(Clientes cliente) {
        cliente.setAtivo(false);
        clienteRepository.save(cliente);
    }

    private Enderecos toEnderecos(EnderecosDTO enderecoDTO) {
        return modelMapper.map(enderecoDTO, Enderecos.class);
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
