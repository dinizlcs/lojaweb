package com.dnz.lojaweb.repository;

import com.dnz.lojaweb.model.ProdutoEntity;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {
    @Autowired
    ProdutoRepository pr;
    
    public ProdutoEntity saveProduto(ProdutoEntity produto){
        produto.setId(null);
        pr.save(produto);
        
        return produto;
    }
    
    public List<ProdutoEntity> getAllProdutos(){
        return pr.findAll();
    }
    
    public ProdutoEntity getProdutoByTitle(String title){
        List<ProdutoEntity> produtosFound = pr.findByTitleContaining(title);
        if(produtosFound.isEmpty()){
            return null;
        }
        
        return produtosFound.get(0);
    }
    
    public ProdutoEntity getProdutoById(Integer idProduto){
        return pr.findById(idProduto).orElse(null);
    }
}
