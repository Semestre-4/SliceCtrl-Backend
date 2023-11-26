package com.mensal.slicectrl.service;

import com.mensal.slicectrl.config.security.JwtServiceGenerator;
import com.mensal.slicectrl.dto.LoginDTO;
import com.mensal.slicectrl.dto.UsuarioDTO;
import com.mensal.slicectrl.entity.Usuario;
import com.mensal.slicectrl.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private LoginRepository repository;
    @Autowired
    private JwtServiceGenerator jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    public UsuarioDTO authenticate(LoginDTO loginDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getCpf(),
                        loginDTO.getPassword()
                )
        );
        Usuario user = repository.findByCpf(loginDTO.getCpf()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);

        return toUsuarioDTO(user, jwtToken);
    }

    private UsuarioDTO toUsuarioDTO(Usuario user, String token) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(user.getId());
        usuarioDTO.setToken(token);
        usuarioDTO.setCpf(user.getCpf());
        usuarioDTO.setRole(user.getRole());
        return usuarioDTO;
    }


}
