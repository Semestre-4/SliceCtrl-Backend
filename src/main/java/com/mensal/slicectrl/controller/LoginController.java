package com.mensal.slicectrl.controller;

import com.mensal.slicectrl.dto.LoginDTO;
import com.mensal.slicectrl.dto.UsuarioDTO;
import com.mensal.slicectrl.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public ResponseEntity<UsuarioDTO> logar(@RequestBody LoginDTO loginDTO) {
        try {
            return ResponseEntity.ok(loginService.logar(loginDTO));
        }catch(AuthenticationException ex) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("deslogar")
    public ResponseEntity<HttpStatus> logout() {
        SecurityContextHolder.clearContext();
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}

