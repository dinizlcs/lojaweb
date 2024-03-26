package com.dnz.lojaweb.repository;

import com.dnz.lojaweb.model.ProdutoEntity;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProdutoService {
    @Autowired
    ProdutoRepository pr;
    
    public ProdutoEntity saveProduto(ProdutoEntity produto){
        produto.setId(null);
        pr.save(produto);
        
        return produto;
    }
    
    public ProdutoEntity updProduto(ProdutoEntity updatedProduct, MultipartFile imgFile){
        ProdutoEntity prodToUpdate = getProdutoById(updatedProduct.getId());
        if(prodToUpdate != null){
            // Verificar se o usuário selecionou uma imagem. Caso não tenha nada, manter a do banco
            if(!imgFile.isEmpty()){
                try{
                    byte[] imgBytes = imgFile.getBytes();
                    prodToUpdate.setImage(imgBytes);
                }catch(IOException e){
                    
                }
            }
            
            // Atualizar atributos do produto pego do banco
            prodToUpdate.setTitle(updatedProduct.getTitle());
            prodToUpdate.setDescription(updatedProduct.getDescription());
            prodToUpdate.setPrice(updatedProduct.getPrice());
        }
        
        pr.save(prodToUpdate);
        return prodToUpdate;
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
