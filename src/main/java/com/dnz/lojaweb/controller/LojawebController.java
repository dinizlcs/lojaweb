package com.dnz.lojaweb.controller;

import com.dnz.lojaweb.model.ProdutoEntity;
import com.dnz.lojaweb.model.UsuarioEntity;
import com.dnz.lojaweb.repository.ProdutoService;
import java.util.Base64;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class LojawebController {
    @Autowired
    ProdutoService ps;
    
    @GetMapping("/")
    public String index(@CookieValue(name="loggedUser", defaultValue="") Integer loggedUserId, @CookieValue(name="isUserLogged", defaultValue="false") boolean isLogged, Model model){
        List<ProdutoEntity> produtos = ps.getAllProdutos();
        
        for(ProdutoEntity p:produtos){
            if(p.getImage() != null){
                byte[] imgBytes = p.getImage();
                String base64Image = Base64.getEncoder().encodeToString(imgBytes);
                p.setBase64Image(base64Image);
            }
        }
        
        model.addAttribute("isUserLogged", isLogged);
        model.addAttribute("lstProdutos", produtos);
        
        return "home";
    }
    
    @GetMapping("/usuario/{regLogin}")
    public String formUser(@CookieValue(name="loggedUser", defaultValue="") Integer loggedUserId, @CookieValue(name="isUserLogged", defaultValue="false") boolean isLogged, Model model,
    @PathVariable(value="regLogin") String regLogin){
        model.addAttribute("isUserLogged", isLogged);
        model.addAttribute("usuario", new UsuarioEntity());

        // Verificar se é para cadastrar e mostrar o form de Cadastro ou de Login
        if(regLogin.equals("cadastro")){
            model.addAttribute("isToSignup", true);
        }else{
            model.addAttribute("isToSignup", false);
        }
        
        return "formUser";
    }
    
    @GetMapping("/cadastrar-produto")
    public String formProduct(@CookieValue(name="isUserLogged", defaultValue="false") boolean isLogged, Model model){
        model.addAttribute("isUserLogged", isLogged);
        model.addAttribute("produto", new ProdutoEntity());
        
        return "formProdRegister";
    }
    
    @GetMapping("/detalhes/{id}")
    public String details(@PathVariable(value="id") Integer id, Model model){
        ProdutoEntity prodFound = ps.getProdutoById(id);
        
        model.addAttribute("produto", prodFound);
        
        return "details";
    }
}
