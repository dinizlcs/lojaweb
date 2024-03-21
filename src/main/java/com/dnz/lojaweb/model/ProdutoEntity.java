package com.dnz.lojaweb.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Data;

@Data
@Entity
@Table(name="tb_produtos")
public class ProdutoEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    
    @Lob
    @Column(columnDefinition="MEDIUMBLOB")
    private byte[] image;
    
    @NotBlank(message="Adicione um títutlo para o produto")
    private String title;
    
    @Column(columnDefinition="TEXT")
    private String description;
    
    @NotNull(message="Adicione um valor para o produto")
    private Double price;
    
    @JsonManagedReference
    @OneToMany(mappedBy="product", cascade=CascadeType.ALL)
    private List<AvaliacoesEntity> reviews;
    
    @Transient
    private String base64Image;
    
    @Transient
    private Double avgRating;
}
