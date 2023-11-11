package com.mensal.slicectrl.repository;

import com.mensal.slicectrl.entity.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfigurationRepository extends JpaRepository<Configuration,Long> {
}
