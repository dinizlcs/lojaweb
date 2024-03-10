package com.dnz.lojaweb.controller;

import com.dnz.lojaweb.model.ProdutoEntity;
import com.dnz.lojaweb.repository.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProdutoController {
    @Autowired
    ProdutoService ps;
    
    @PostMapping("/registerProduct")
    public String prodRegister(@ModelAttribute("produto") ProdutoEntity produto){
        if(produto.getImage().length == 0){
            produto.setImage(null);
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
