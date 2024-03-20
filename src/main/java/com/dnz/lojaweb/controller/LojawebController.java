package com.dnz.lojaweb.controller;

import com.dnz.lojaweb.model.ProdutoEntity;
import com.dnz.lojaweb.model.UsuarioEntity;
import com.dnz.lojaweb.repository.ProdutoService;
import java.util.Base64;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LojawebController {
    @Autowired
    ProdutoService ps;
    
    @GetMapping("/")
    public ModelAndView index(@CookieValue(name="loggedUser", defaultValue="") Integer loggedUserId, @CookieValue(name="isUserLogged", defaultValue="false") boolean isLogged){
        ModelAndView mv = new ModelAndView("home");
        List<ProdutoEntity> produtos = ps.getAllProdutos();
        
        for(ProdutoEntity p:produtos){
            if(p.getImage() != null){
                byte[] imgBytes = p.getImage();
                String base64Image = Base64.getEncoder().encodeToString(imgBytes);
                p.setBase64Image(base64Image);
            }
        }
        
        mv.addObject("isUserLogged", isLogged);
        mv.addObject("lstProdutos", produtos);
        return mv;
    }
    
    @GetMapping("/usuario/{regLogin}")
    public ModelAndView formUser(@CookieValue(name="loggedUser", defaultValue="") Integer loggedUserId, @CookieValue(name="isUserLogged", defaultValue="false") boolean isLogged,
    @PathVariable(value="regLogin") String regLogin){
        ModelAndView mv = new ModelAndView("formUser");
        
        mv.addObject("isUserLogged", isLogged);
        mv.addObject("usuario", new UsuarioEntity());

        // Verificar se é para cadastrar e mostrar o form de Cadastro ou de Login
        if(regLogin.equals("cadastro")){
            mv.addObject("isToSignup", true);
        }else{
            mv.addObject("isToSignup", false);
        }
        return mv;
    }
    
    @GetMapping("/cadastrar-produto")
    public ModelAndView formProduct(@CookieValue(name="isUserLogged", defaultValue="false") boolean isLogged){
        ModelAndView mv = new ModelAndView("formProdRegister");
        
        mv.addObject("isUserLogged", isLogged);
        mv.addObject("produto", new ProdutoEntity());
        return mv;
    }
}
