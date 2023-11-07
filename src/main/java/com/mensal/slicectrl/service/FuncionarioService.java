package com.mensal.slicectrl.service;

import com.mensal.slicectrl.dto.FuncionariosDTO;
import com.mensal.slicectrl.dto.PedidosDTO;
import com.mensal.slicectrl.entity.Funcionarios;
import com.mensal.slicectrl.entity.Pedidos;
import com.mensal.slicectrl.repository.FuncionarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
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
            throw new IllegalArgumentException("Ocorreu um erro ao tentar recuperar o registro do funcionário.", ex);
        }
    }

    public List<FuncionariosDTO> findByNome(String nome) {
        return funcionarioRepository.findByNome(nome).stream().map(this::toFuncDTO).toList();
    }

    public List<FuncionariosDTO> findByAtivo(boolean ativo) {
        return funcionarioRepository.findByAtivo(ativo).stream().map(this::toFuncDTO).toList();
    }

    public FuncionariosDTO findByCPF(String cpf) {
        return toFuncDTO(funcionarioRepository.findByCpf(cpf));
    }

    public List<FuncionariosDTO> findAll() {
        return funcionarioRepository.findAll().stream().map(this::toFuncDTO).toList();
    }

    @Transactional
    public Funcionarios createFuncionario(FuncionariosDTO funcionariosDTO) {
        // Check if the CPF already exists
        if (funcionarioRepository.existsByCpf(funcionariosDTO.getCpf())) {
            throw new IllegalArgumentException("Funcionario com CPF = " + funcionariosDTO.getCpf() + " já existe");
        }
        Funcionarios funcionarios = toFunc(funcionariosDTO);
        return funcionarioRepository.save(funcionarios);
    }

    @Transactional
    public Funcionarios updateFuncionario(Long id, FuncionariosDTO funcionariosDTO) {

        if (!funcionarioRepository.existsById(id)) {
            throw new EntityNotFoundException("Funcionario com ID = " + id + " nao encontrado");
        }

        if (!id.equals(funcionariosDTO.getId())) {
            throw new IllegalArgumentException("O ID na URL não corresponde ao ID no corpo da requisição");
        }

        if (funcionarioRepository.existsByCpfAndIdNot(funcionariosDTO.getCpf(), id)) {
            throw new IllegalArgumentException("CPF já está sendo usado por outro Funcionario");
        }

        Funcionarios funcionarios = toFunc(funcionariosDTO);
        return funcionarioRepository.save(funcionarios);
    }


    @Transactional
    public void deleteFuncionario(Long id) {
        Funcionarios funcToDelete = funcionarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Funcionario com ID = : " + id + " não foi encontrado"));

        funcToDelete.setAtivo(false);
        funcionarioRepository.save(funcToDelete);
    }

    public FuncionariosDTO toFuncDTO(Funcionarios funcionarios) {
        return modelMapper.map(funcionarios, FuncionariosDTO.class);
    }

    public Funcionarios toFunc(FuncionariosDTO funcionariosDTO) {
        return modelMapper.map(funcionariosDTO, Funcionarios.class);
    }

}
