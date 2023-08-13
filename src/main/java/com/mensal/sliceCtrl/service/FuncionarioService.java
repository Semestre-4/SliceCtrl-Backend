package com.mensal.sliceCtrl.service;

import com.mensal.sliceCtrl.DTO.ClienteDTO;
import com.mensal.sliceCtrl.DTO.FuncionarioDTO;
import com.mensal.sliceCtrl.DTO.PedidoDTO;
import com.mensal.sliceCtrl.entity.Cliente;
import com.mensal.sliceCtrl.entity.Funcionario;
import com.mensal.sliceCtrl.entity.Pedido;
import com.mensal.sliceCtrl.repository.FuncionarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public FuncionarioService(FuncionarioRepository funcionarioRepository, ModelMapper modelMapper) {
        this.funcionarioRepository = funcionarioRepository;
        this.modelMapper = modelMapper;
    }

    public FuncionarioDTO findById(Long id) {
        try {
            Funcionario funcionarioEncontrado = funcionarioRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Funcionário não encontrado: " + id));
            return toFuncDTO(funcionarioEncontrado);
        } catch (EntityNotFoundException ex) {
            throw new RuntimeException("Ocorreu um erro ao tentar recuperar o registro do funcionário.", ex);
        }
    }

    public List<FuncionarioDTO> findByNome(String nome) {
        return funcionarioRepository.findByNome(nome).stream().map(this::toFuncDTO).toList();
    }

    public FuncionarioDTO findByCPF(String cpf) {
        return toFuncDTO(funcionarioRepository.findByCpf(cpf));
    }

    public List<FuncionarioDTO> findAll() {
        return funcionarioRepository.findAll().stream().map(this::toFuncDTO).toList();
    }

    public Funcionario createFuncionario(FuncionarioDTO funcionarioDTO) {
        Funcionario funcionario = toFunc(funcionarioDTO);
        return funcionarioRepository.save(funcionario);
    }

    public Funcionario updateFuncionario(Long id, FuncionarioDTO funcionarioDTO) {
        Funcionario existingFunc = funcionarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Funcionario com ID = " + id + " nao encontrado"));

        Funcionario funcionario = toFunc(funcionarioDTO);
        return funcionarioRepository.save(funcionario);
    }

    public void deleteFuncionario(Long id) {
        Funcionario funcToDelete = funcionarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Funcionario com ID = : " + id + "nao encontrado"));
        funcionarioRepository.delete(funcToDelete);
    }


    private Pedido toPedido(PedidoDTO pedidoDTO) {
        return modelMapper.map(pedidoDTO, Pedido.class);
    }

    public FuncionarioDTO toFuncDTO(Funcionario funcionario) {
        return modelMapper.map(funcionario, FuncionarioDTO.class);
    }

    public Funcionario toFunc(FuncionarioDTO funcionarioDTO) {
        return modelMapper.map(funcionarioDTO, Funcionario.class);
    }

}
