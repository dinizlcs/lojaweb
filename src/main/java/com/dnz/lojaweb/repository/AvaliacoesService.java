package com.dnz.lojaweb.repository;

import com.dnz.lojaweb.model.AvaliacoesEntity;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AvaliacoesService {
    @Autowired
    AvaliacoesRepository ar;
    
    public AvaliacoesEntity saveAvaliacao(AvaliacoesEntity avaliacao){
        avaliacao.setId(null);
        ar.save(avaliacao);
        
        return avaliacao;
    }
    
    public List<AvaliacoesEntity> getFirst6Avaliacoes(Integer idProduto){
        List<AvaliacoesEntity> prodReviews = ar.findTop6ByProductIdOrderByIdDesc(idProduto);
        if(prodReviews.isEmpty()){
            return null;
        }
        
        return prodReviews;
    }
    
    public List<AvaliacoesEntity> getAllAvaliacoes(Integer idProduto){
        List<AvaliacoesEntity> prodReviews = ar.findAllByProductId(idProduto);
        if(prodReviews.isEmpty()){
            return null;
        }
        
        return prodReviews;
    }
    
    public Integer getCountAvaliacoes(Integer idProduto){
        return ar.countAvaliacoes(idProduto);
    }
    
    public Double getAvgRatingAvaliacoes(Integer idProduto){
        Double avgRating = ar.avgRatingAvaliacoes(idProduto);
        if(avgRating == null){
            return 0.0;
        }
        
        return avgRating;
    }
}
