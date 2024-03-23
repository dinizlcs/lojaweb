package com.dnz.lojaweb.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name="tb_usuarios")
public class UsuarioEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    
    @Column(unique=true)
    @NotBlank(message="Preencha o email")
    private String email;
    
    @NotBlank(message="Digite uma senha")
    @Size(min=5, message="A senha deve ter no mínimo 5 caracteres")
    private String password;
    
    private String accessLvl;
    
    public UsuarioEntity(){
        // Ao criar um novo usuário por padrão o seu nível de acesso é o "comum"
        this.accessLvl = "common";
    }
}
