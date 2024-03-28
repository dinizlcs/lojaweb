package com.dnz.lojaweb.controller;

import com.dnz.lojaweb.model.AvaliacoesEntity;
import com.dnz.lojaweb.model.ProdutoEntity;
import com.dnz.lojaweb.repository.AvaliacoesService;
import com.dnz.lojaweb.utils.SessionManager;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reviews")
public class AvaliacoesController {
    @Autowired
    AvaliacoesService as;
    
    @Autowired
    SessionManager sm;
    
    @PostMapping("/registerAnalise")
    public String revRegister(@ModelAttribute("analise") AvaliacoesEntity avaliacao, @ModelAttribute("produto") ProdutoEntity produto, HttpSession session){
        if(sm.isUserLogged(session)){
            avaliacao.setProduct(produto);
            as.saveAvaliacao(avaliacao);
            return "redirect:/product/detalhes/" + avaliacao.getProduct().getId();
        }else{
            return "redirect:/usuario/login";
        }
    }
}
