package com.dnz.lojaweb.controller;

import com.dnz.lojaweb.model.ProdutoEntity;
import com.dnz.lojaweb.model.UsuarioEntity;
import com.dnz.lojaweb.repository.AvaliacoesService;
import com.dnz.lojaweb.repository.ProdutoService;
import com.dnz.lojaweb.utils.SessionManager;
import jakarta.servlet.http.HttpSession;
import java.util.Base64;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LojawebController {
    @Autowired
    ProdutoService ps;
    
    @Autowired
    AvaliacoesService as;
    
    @Autowired
    SessionManager sm;
     
    @GetMapping("/")
    public ModelAndView index(HttpSession session){
        ModelAndView mv = new ModelAndView("home");
        List<ProdutoEntity> produtos = ps.getAllProdutos();
        
        for(ProdutoEntity p:produtos){
            if(p.getImage() != null){
                byte[] imgBytes = p.getImage();
                String base64Image = Base64.getEncoder().encodeToString(imgBytes);
                p.setBase64Image(base64Image);
            }
            
            p.setAvgRating(as.getAvgRatingAvaliacoes(p.getId()));
        }
        
        mv.addObject("navUser", sm.getLoggedUser(session));
        mv.addObject("lstProdutos", produtos);
        return mv;
    }
    
    @GetMapping("/usuario/{regLogin}")
    public ModelAndView formUser(@PathVariable(value="regLogin") String regLogin, HttpSession session){
        ModelAndView mv = new ModelAndView("formUser");
        
        mv.addObject("navUser", sm.getLoggedUser(session));
        mv.addObject("usuario", new UsuarioEntity());

        // Verificar se é para cadastrar e mostrar o form de Cadastro ou de Login
        if(regLogin.equals("cadastro")){
            mv.addObject("isToSignup", true);
        }else{
            mv.addObject("isToSignup", false);
        }
        return mv;
    }
    
    @GetMapping("/carrinho")
    public ModelAndView cart(HttpSession session){
        if(sm.isUserLogged(session)){
            ModelAndView mv = new ModelAndView("cart");

            List<ProdutoEntity> cart = sm.getCart(session);
            double total = 0;
            for(ProdutoEntity p : cart){
                total += p.getPrice();
            }

            mv.addObject("navUser", sm.getLoggedUser(session));
            mv.addObject("lstCart", cart);
            mv.addObject("totalValue", total);
            return mv;
        }
        
        return new ModelAndView("redirect:/usuario/login");
    }
    
    @GetMapping("/compra-finalizada")
    public String purFinalized(HttpSession session){
        if(sm.isUserLogged(session)){
            sm.clearCart(session);
            return "purFinalized";
        }else{
            return "redirect:/";
        }
    }
    
    @GetMapping("/cadastrar-produto")
    public ModelAndView formRegisterProduct(HttpSession session){
        if(sm.isUserLogged(session) && sm.getLoggedUser(session).getAccessLvl().equals("admin")){
            ModelAndView mv = new ModelAndView("formProdRegister");
        
            mv.addObject("navUser", sm.getLoggedUser(session));
            mv.addObject("produto", new ProdutoEntity());
            return mv;
        }
        
        ModelAndView mv = new ModelAndView("redirect:/");
        return mv;
    }
    
    @GetMapping("/editar-produto")
    public ModelAndView formEditProduct(HttpSession session){
        if(sm.isUserLogged(session) && sm.getLoggedUser(session).getAccessLvl().equals("admin")){
            ModelAndView mv = new ModelAndView("formProdEdit");
        
            mv.addObject("navUser", sm.getLoggedUser(session));
            mv.addObject("lstProdutos", ps.getAllProdutos());
            return mv;
        }
        
        return new ModelAndView("redirect:/");
    }
}
