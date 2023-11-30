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

import java.nio.file.AccessDeniedException;
import java.security.InvalidKeyException;

@RestController
@RequestMapping("/api/login")
@CrossOrigin("http://localhost:4200")
public class LogInController {

    @Autowired  private LoginService loginService;

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody LoginDTO loginDTO) throws AccessDeniedException, InvalidKeyException {
        try {
            UsuarioDTO authenticatedUser = loginService.authenticate(loginDTO);
            return ResponseEntity.ok(authenticatedUser);
        } catch (AuthenticationException ex) {
            return new ResponseEntity<>("Authentication failed: " + ex.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>("Bad request: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/logout")
    public ResponseEntity<HttpStatus> logout() {
        SecurityContextHolder.clearContext();
        return new ResponseEntity<>(null, HttpStatus.OK);

    }


}
