package com.mensal.sliceCtrl.service;

import com.mensal.sliceCtrl.DTO.FuncionariosDTO;
import com.mensal.sliceCtrl.DTO.PedidosDTO;
import com.mensal.sliceCtrl.entity.Funcionarios;
import com.mensal.sliceCtrl.entity.Pedidos;
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

    public FuncionariosDTO findById(Long id) {
        try {
            Funcionarios funcionariosEncontrado = funcionarioRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Funcionário não encontrado: " + id));
            return toFuncDTO(funcionariosEncontrado);
        } catch (EntityNotFoundException ex) {
            throw new RuntimeException("Ocorreu um erro ao tentar recuperar o registro do funcionário.", ex);
        }
    }

    public List<FuncionariosDTO> findByNome(String nome) {
        return funcionarioRepository.findByNome(nome).stream().map(this::toFuncDTO).toList();
    }

    public FuncionariosDTO findByCPF(String cpf) {
        return toFuncDTO(funcionarioRepository.findByCpf(cpf));
    }

    public List<FuncionariosDTO> findAll() {
        return funcionarioRepository.findAll().stream().map(this::toFuncDTO).toList();
    }

    public Funcionarios createFuncionario(FuncionariosDTO funcionariosDTO) {
        // Check if the CPF already exists
        if (funcionarioRepository.existsByCpf(funcionariosDTO.getCpf())) {
            throw new IllegalArgumentException("Funcionario com CPF = " + funcionariosDTO.getCpf() + " já existe");
        }
        Funcionarios funcionarios = toFunc(funcionariosDTO);
        return funcionarioRepository.save(funcionarios);
    }

    public Funcionarios updateFuncionario(Long id, FuncionariosDTO funcionariosDTO) {
        Funcionarios existingFunc = funcionarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Funcionario com ID = " + id + " nao encontrado"));

        if (!id.equals(funcionariosDTO.getId())) {
            throw new IllegalArgumentException("O ID na URL não corresponde ao ID no corpo da requisição");
        }

        if (funcionarioRepository.existsByCpfAndIdNot(funcionariosDTO.getCpf(), id)) {
            throw new IllegalArgumentException("CPF já está sendo usado por outro Funcionario");
        }

        Funcionarios funcionarios = toFunc(funcionariosDTO);
        return funcionarioRepository.save(funcionarios);
    }


    public void deleteFuncionario(Long id) {
        Funcionarios funcToDelete = funcionarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Funcionario com ID = : " + id + " não foi encontrado"));
        funcionarioRepository.delete(funcToDelete);
    }


    private Pedidos toPedido(PedidosDTO pedidosDTO) {
        return modelMapper.map(pedidosDTO, Pedidos.class);
    }

    public FuncionariosDTO toFuncDTO(Funcionarios funcionarios) {
        return modelMapper.map(funcionarios, FuncionariosDTO.class);
    }

    public Funcionarios toFunc(FuncionariosDTO funcionariosDTO) {
        return modelMapper.map(funcionariosDTO, Funcionarios.class);
    }

}
