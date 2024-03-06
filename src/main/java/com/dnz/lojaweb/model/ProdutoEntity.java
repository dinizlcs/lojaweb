package com.dnz.lojaweb.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
    
    @NotBlank(message="Adicione um títutlo para o produto")
    private String prodTitle;
    
    @Column(columnDefinition="TEXT")
    private String prodDescription;
    
    @NotNull(message="Adicione um valor para o produto")
    private Double prodValue;
    
    @JsonManagedReference
    @OneToMany(mappedBy="product", cascade=CascadeType.ALL)
    private List<AvaliacoesEntity> prodReviews;
}
