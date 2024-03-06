package com.dnz.lojaweb.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name="tb_avaliacoes")
public class AvaliacoesEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    
    @NotNull(message="Escolha uma nota para avaliar o produto")
    private Integer rating;
    
    private String review;
    
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="id_produto")
    private ProdutoEntity product;
}
