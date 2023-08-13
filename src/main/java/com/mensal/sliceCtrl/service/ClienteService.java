package com.mensal.sliceCtrl.service;

import com.mensal.sliceCtrl.DTO.ClienteDTO;
import com.mensal.sliceCtrl.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    public ClienteRepository clienteRepository;

    public ClienteDTO findById(Long id) {
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
}
