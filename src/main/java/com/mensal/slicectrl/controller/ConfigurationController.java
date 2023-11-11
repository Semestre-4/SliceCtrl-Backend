package com.mensal.slicectrl.controller;

import com.mensal.slicectrl.entity.Configuration;
import com.mensal.slicectrl.service.ConfigurationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/config")
@AllArgsConstructor
public class ConfigurationController {

    private ConfigurationService configurationService;

    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO_TECNICO')")
    @GetMapping("/all")
    public ResponseEntity<List<Configuration>> getAll() {
        List<Configuration> configurations = configurationService.findAll();
        return ResponseEntity.ok(configurations);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO_TECNICO')")
    @PostMapping
    public ResponseEntity<Configuration> post(@RequestBody @Validated Configuration configuration) {
            return ResponseEntity.ok(configurationService.post(configuration));
    }
}
