package com.dnz.lojaweb.repository;

import com.dnz.lojaweb.model.UsuarioEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository ur;
    
    public UsuarioEntity saveUsuario(UsuarioEntity user){
        user.setId(null);
        ur.save(user);
        
        return user;
    }
    
    public UsuarioEntity getUserByEmail(String email){
        return ur.findByEmail(email);
    }
}
