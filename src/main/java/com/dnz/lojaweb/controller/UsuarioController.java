package com.dnz.lojaweb.controller;

import com.dnz.lojaweb.model.UsuarioEntity;
import com.dnz.lojaweb.repository.UsuarioService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UsuarioController {
    @Autowired
    private UsuarioService us;
    
    @PostMapping("/signupUser")
    public String registerUser(@CookieValue(name="isUserLogged", defaultValue="false") boolean isLogged, @Valid @ModelAttribute("usuario") UsuarioEntity user, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("isUserLogged", isLogged);
            model.addAttribute("isToSignup", true);
            return "formUser";
        }
        
        try{
            us.saveUsuario(user);
        }catch(DataIntegrityViolationException e){
            result.rejectValue("email", "error.user", "o email já está cadastrado.");
            model.addAttribute("isUserLogged", isLogged);
            model.addAttribute("isToSignup", true);
            return "formUser";
        }
        
        return "redirect:/usuario/login";
    }
    @PostMapping("/loginUser")
    public String loginUser(@ModelAttribute("usuario") UsuarioEntity user, HttpServletResponse response, Model model){
        UsuarioEntity userFound = us.getUserEmail(user.getEmail());
        
        if(userFound != null && userFound.getEmail().equals(user.getEmail()) && userFound.getPassword().equals(user.getPassword())){
            Cookie cookieUser = new Cookie("loggedUser", String.valueOf(userFound.getId()));
            cookieUser.setHttpOnly(true);
            response.addCookie(cookieUser);

            Cookie cookieIsLogged = new Cookie("isUserLogged", "true");
            cookieUser.setHttpOnly(true);
            response.addCookie(cookieIsLogged);
            
            return "redirect:/";
        }else{
            model.addAttribute("isToSignup", false);
            return "redirect:/usuario/login";
        }
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
