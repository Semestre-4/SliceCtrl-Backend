package com.mensal.slicectrl.service;

import com.mensal.slicectrl.entity.Configuration;
import com.mensal.slicectrl.repository.ConfigurationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ConfigurationService {

    private ConfigurationRepository configurationRepository;

    public List<Configuration> findAll() {
        return configurationRepository.findAll().stream().toList();
    }

    public Configuration post(Configuration configuration) {
        return configurationRepository.save(configuration);
    }
}
