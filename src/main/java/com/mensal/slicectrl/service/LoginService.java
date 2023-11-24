//AuthenticationService.java
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


	public UsuarioDTO logar(LoginDTO loginDTO) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						loginDTO.getUsername(),
						loginDTO.getPassword()
						)
				);
		Usuario user = repository.findByUsername(loginDTO.getUsername()).orElseThrow();
		var jwtToken = jwtService.generateToken(user);
		
		return toUserDTO(user, jwtToken);
	}


	private UsuarioDTO toUserDTO(Usuario user, String token) {
		UsuarioDTO userDTO = new UsuarioDTO();
		userDTO.setId(user.getId());
		userDTO.setRoles(user.getRoles());
		userDTO.setToken(token);
		userDTO.setUsername(user.getUsername());
		return userDTO;
	}

}
