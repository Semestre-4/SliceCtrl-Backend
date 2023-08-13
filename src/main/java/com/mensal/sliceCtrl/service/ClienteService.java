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
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return null;
    }

    public List<ClienteDTO> findByNome(String nome) {
        return null;
    }

    public List<ClienteDTO> findByCPF(String cpf) {
        return null;
    }

    public List<ClienteDTO> findAll() {
        return null;
    }

    public ClienteDTO createCliente(ClienteDTO clienteDTO) {
        return null;
    }

    public ClienteDTO updateCliente(Long id, ClienteDTO clienteDTO) {
        return null;
    }

    public void deleteCliente(Long id) {
    }

    public ClienteDTO toClienteDTO(Cliente cliente) {
        return modelMapper.map(cliente, ClienteDTO.class);
    }

    public Cliente toCliente(ClienteDTO clienteDTO) {
        return modelMapper.map(clienteDTO, Cliente.class);
    }


}
