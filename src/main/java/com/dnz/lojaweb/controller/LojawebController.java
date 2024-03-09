package com.dnz.lojaweb.controller;

import com.dnz.lojaweb.model.ProdutoEntity;
import com.dnz.lojaweb.model.UsuarioEntity;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class LojawebController {
    private List<ProdutoEntity> produtos = new ArrayList();
    private boolean genProducts = true;
    
    @GetMapping("/")
    public String index(@CookieValue(name="loggedUser", defaultValue="") Integer loggedUserId, @CookieValue(name="isUserLogged", defaultValue="false") boolean isLogged, Model model){
        if(genProducts){
            ProdutoEntity prodUm = new ProdutoEntity();
            prodUm.setId(1);
            prodUm.setImage(null);
            prodUm.setTitle("Teste título um");
            prodUm.setDescription("Teste de uma descrição do produto Um");
            prodUm.setPrice(10.99);
            ProdutoEntity prodDois = new ProdutoEntity();
            prodDois.setId(2);
            prodDois.setImage(null);
            prodDois.setTitle("Teste título dois");
            prodDois.setDescription("Teste de uma descrição mais longa que a do produto Um para o produto Dois");
            prodDois.setPrice(20.0);

            produtos.add(prodUm);
            produtos.add(prodDois);

            for(int i = 3; i < 10; i++){
                ProdutoEntity prodX = new ProdutoEntity();
                prodX.setId(i);
                prodX.setImage(null);
                prodX.setTitle("Teste título " + i);
                prodX.setDescription("Teste de uma descrição mais longa que a do produto Um para o produto " + i);
                prodX.setPrice(Double.valueOf(i) * 10);
                produtos.add(prodX);
            }
            
            genProducts = false;
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
        
        return "formProdRegister";
    }
    
    @GetMapping("/detalhes/{id}")
    public String details(@PathVariable(value="id") Integer id, Model model){
        ProdutoEntity prodFound = produtos.get(id - 1);
        
        model.addAttribute("produto", prodFound);
        
        return "details";
    }
}
