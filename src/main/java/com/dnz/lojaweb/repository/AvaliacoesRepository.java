package com.dnz.lojaweb.repository;

import com.dnz.lojaweb.model.AvaliacoesEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AvaliacoesRepository extends JpaRepository<AvaliacoesEntity, Integer> {

    public List<AvaliacoesEntity> findAllByProductId(Integer idProduto);
    
    @Query("SELECT COUNT(av) FROM AvaliacoesEntity av WHERE av.product.id = :idProduto")
    public Integer countAvaliacoes(@Param("idProduto") Integer idProduto);

    public List<AvaliacoesEntity> findTop6ByProductIdOrderByIdDesc(Integer idProduto);

    @Query("SELECT ROUND(AVG(av.rating), 2) FROM AvaliacoesEntity av WHERE av.product.id = :idProduto")
    public Double avgRatingAvaliacoes(@Param("idProduto") Integer idProduto);
}
