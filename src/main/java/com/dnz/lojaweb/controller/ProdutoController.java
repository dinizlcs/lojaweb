package com.dnz.lojaweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProdutoController {
    @PostMapping("/registerProduct")
    public String prodRegister(){
        
        
        return "redirect:/cadastrar-produto";
    }
}
