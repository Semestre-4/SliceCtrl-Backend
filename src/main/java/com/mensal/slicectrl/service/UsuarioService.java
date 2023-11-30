package com.mensal.slicectrl.service;

import com.mensal.slicectrl.dto.UsuarioDTO;
import com.mensal.slicectrl.dto.UsuarioFrontDTO;
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
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UsuarioFrontDTO findById(Long id) {
        try {
            Usuario usuarioEncontrado = usuarioRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Funcionário não encontrado: " + id));
            return toUsuarioFrontDTO(usuarioEncontrado);
        } catch (EntityNotFoundException ex) {
            throw new IllegalArgumentException("Ocorreu um erro ao tentar recuperar o registro do funcionário.", ex);
        }
    }

    public List<UsuarioFrontDTO> findByAtivo(boolean ativo) {
        return usuarioRepository.findByAtivo(ativo).stream().map(this::toUsuarioFrontDTO).toList();
    }

    public UsuarioFrontDTO findByCPF(String cpf) {
        return toUsuarioFrontDTO(usuarioRepository.findByCpf(cpf));
    }

    public List<UsuarioFrontDTO> findAll() {
        return usuarioRepository.findAll().stream().map(this::toUsuarioFrontDTO).toList();
    }

    @Transactional
    public Usuario createUsuario(Usuario usuario) {
        if (usuarioRepository.existsByCpf(usuario.getCpf())) {
            throw new IllegalArgumentException("Funcionario com CPF = " + usuario.getCpf() + " já existe");
        }
        String encodedPassword = passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(encodedPassword);
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario updateUsuario(Long id, Usuario usuario) {

        if (!usuarioRepository.existsById(id)) {
            throw new EntityNotFoundException("Funcionario com ID = " + id + " nao encontrado");
        }

        if (!id.equals(usuario.getId())) {
            throw new IllegalArgumentException("O ID na URL não corresponde ao ID no corpo da requisição");
        }

        if (usuarioRepository.existsByCpfAndIdNot(usuario.getCpf(), id)) {
            throw new IllegalArgumentException("CPF já está sendo usado por outro Funcionario");
        }
        String encodedPassword = passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(encodedPassword);
        return usuarioRepository.save(usuario);
    }


    @Transactional
    public void deleteFuncionario(Long id) {
        Usuario funcToDelete = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Funcionario com ID = : " + id + " não foi encontrado"));

        funcToDelete.setAtivo(false);
        usuarioRepository.save(funcToDelete);
    }

    public UsuarioFrontDTO toUsuarioFrontDTO(Usuario usuario) {
        return modelMapper.map(usuario, UsuarioFrontDTO.class);
    }

    public Usuario toFunc(UsuarioDTO usuarioDTO) {
        return modelMapper.map(usuarioDTO, Usuario.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByCpf(username);
    }

    public List<UsuarioFrontDTO> findByNome(String nome) {
        return usuarioRepository.findByNome(nome).stream().map(this::toUsuarioFrontDTO).toList();
    }
}
