package com.mensal.slicectrl.service;

import com.mensal.slicectrl.dto.UsuarioDTO;
import com.mensal.slicectrl.entity.Usuario;
import com.mensal.slicectrl.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, ModelMapper modelMapper) {
        this.usuarioRepository = usuarioRepository;
        this.modelMapper = modelMapper;
    }

    public UsuarioDTO findById(Long id) {
        try {
            Usuario usuarioEncontrado = usuarioRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Funcionário não encontrado: " + id));
            return toFuncDTO(usuarioEncontrado);
        } catch (EntityNotFoundException ex) {
            throw new IllegalArgumentException("Ocorreu um erro ao tentar recuperar o registro do funcionário.", ex);
        }
    }

    private PasswordEncoder encode(){
        return new BCryptPasswordEncoder();
    }

    public List<UsuarioDTO> findByNome(String nome) {
        return usuarioRepository.findByNome(nome).stream().map(this::toFuncDTO).toList();
    }

    public List<UsuarioDTO> findByAtivo(boolean ativo) {
        return usuarioRepository.findByAtivo(ativo).stream().map(this::toFuncDTO).toList();
    }

    public UsuarioDTO findByCPF(String cpf) {
        return toFuncDTO(usuarioRepository.findByCpf(cpf));
    }

    public List<UsuarioDTO> findAll() {
        return usuarioRepository.findAll().stream().map(this::toFuncDTO).toList();
    }

    @Transactional
    public Usuario createFuncionario(UsuarioDTO usuarioDTO) {
        if (usuarioRepository.existsByCpf(usuarioDTO.getCpf())) {
            throw new IllegalArgumentException("Funcionario com CPF = " + usuarioDTO.getCpf() + " já existe");
        }
        usuarioDTO.setPassword(encode().encode(usuarioDTO.getPassword()));
        Usuario usuario = toFunc(usuarioDTO);
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario updateFuncionario(Long id, UsuarioDTO usuarioDTO) {

        if (!usuarioRepository.existsById(id)) {
            throw new EntityNotFoundException("Funcionario com ID = " + id + " nao encontrado");
        }

        if (!id.equals(usuarioDTO.getId())) {
            throw new IllegalArgumentException("O ID na URL não corresponde ao ID no corpo da requisição");
        }

        if (usuarioRepository.existsByCpfAndIdNot(usuarioDTO.getCpf(), id)) {
            throw new IllegalArgumentException("CPF já está sendo usado por outro Funcionario");
        }

        Usuario usuario = toFunc(usuarioDTO);
        return usuarioRepository.save(usuario);
    }


    @Transactional
    public void deleteFuncionario(Long id) {
        Usuario funcToDelete = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Funcionario com ID = : " + id + " não foi encontrado"));

        funcToDelete.setAtivo(false);
        usuarioRepository.save(funcToDelete);
    }

    public UsuarioDTO toFuncDTO(Usuario usuario) {
        return modelMapper.map(usuario, UsuarioDTO.class);
    }

    public Usuario toFunc(UsuarioDTO usuarioDTO) {
        return modelMapper.map(usuarioDTO, Usuario.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByCpf(username);
    }
}
