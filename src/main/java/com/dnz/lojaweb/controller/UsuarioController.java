package com.dnz.lojaweb.controller;

import com.dnz.lojaweb.model.UsuarioEntity;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UsuarioController {
    @PostMapping("/signupUser")
    public String registerUser(@ModelAttribute("usuario") UsuarioEntity user){
        System.out.println("Usuário cadastrado");
        return "redirect:/";
    }
    @PostMapping("/loginUser")
    public String loginUser(@ModelAttribute("usuario") UsuarioEntity user, HttpServletResponse response){
        System.out.println("Usuário loagado");
        user.setId(1);
        Cookie cookieUser = new Cookie("loggedUser", String.valueOf(user.getId()));
        cookieUser.setHttpOnly(true);
        cookieUser.setMaxAge(86400);
        response.addCookie(cookieUser);
        
        Cookie cookieIsLogged = new Cookie("isUserLogged", "true");
        cookieUser.setHttpOnly(true);
        cookieUser.setMaxAge(86400);
        response.addCookie(cookieIsLogged);
        
        return "redirect:/";
    }
    
    @GetMapping("/logoutUser")
    public String logoutUser(HttpServletResponse response){
        Cookie cookieUser = new Cookie("loggedUser", null);
        cookieUser.setMaxAge(0);
        response.addCookie(cookieUser);
        
        Cookie cookieIsLogged = new Cookie("isUserLogged", null);
        cookieUser.setMaxAge(0);
        response.addCookie(cookieIsLogged);
        
        return "redirect:/";
    }
}
