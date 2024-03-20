package com.dnz.lojaweb.controller;

import com.dnz.lojaweb.model.ProdutoEntity;
import com.dnz.lojaweb.repository.ProdutoService;
import java.io.IOException;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/product")
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
            return "redirect:/product/detalhes/" + prodFound.getId();
        }else{
            return "redirect:/";
        }
    }
    
    @GetMapping("/detalhes/{id}")
    public String details(@PathVariable(value="id") Integer id, Model model){
        ProdutoEntity prodFound = ps.getProdutoById(id);
        if(prodFound.getImage() != null){
            byte[] imgBytes = prodFound.getImage();
            String base64Image = Base64.getEncoder().encodeToString(imgBytes);
            prodFound.setBase64Image(base64Image);
        }
        
        model.addAttribute("produto", prodFound);
        
        return "details";
    }
}
