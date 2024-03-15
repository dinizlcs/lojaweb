package com.dnz.lojaweb.controller;

import com.dnz.lojaweb.model.ProdutoEntity;
import com.dnz.lojaweb.repository.ProdutoService;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ProdutoController {
    @Autowired
    ProdutoService ps;
    
    @PostMapping("/registerProduct")
    public String prodRegister(@ModelAttribute("produto") ProdutoEntity produto, @RequestParam("formImage") MultipartFile imgFile){
        try {
            if(imgFile.isEmpty()){
                produto.setImage(null);
            }else{
                byte[] imgBytes = imgFile.getBytes();
                produto.setImage(imgBytes);
            }
        } catch(IOException ex){
            return "redirect:/cadastrar-produto";
        }
        
        
        ps.saveProduto(produto);
        
        return "redirect:/cadastrar-produto";
    }
    
    @PostMapping("/searchProduto")
    public String prodSearch(@RequestParam("tituloProduto") String tituloProduto){
        ProdutoEntity prodFound = ps.getProdutoByTitle(tituloProduto);
        if(prodFound != null){
            return "redirect:/detalhes/" + prodFound.getId();
        }else{
            return "redirect:/";
        }
    }
}
