package com.dnz.lojaweb.repository;

import com.dnz.lojaweb.model.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Integer> {

    public UsuarioEntity findByEmail(String email);
    
}
