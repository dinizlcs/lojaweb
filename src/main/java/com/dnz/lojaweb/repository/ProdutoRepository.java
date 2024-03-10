package com.dnz.lojaweb.repository;

import com.dnz.lojaweb.model.ProdutoEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<ProdutoEntity, Integer> {

    public List<ProdutoEntity> findByTitleContaining(String title);
    
}
