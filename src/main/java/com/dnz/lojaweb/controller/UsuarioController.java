package com.dnz.lojaweb.controller;

import com.dnz.lojaweb.model.UsuarioEntity;
import com.dnz.lojaweb.repository.UsuarioService;
import com.dnz.lojaweb.utils.SessionManager;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UsuarioController {
    @Autowired
    private UsuarioService us;
    
    @Autowired
    private SessionManager sm;
    
    @PostMapping("/signupUser")
    public String registerUser(@Valid @ModelAttribute("usuario") UsuarioEntity user, BindingResult result, Model model, HttpSession session){
        if(result.hasErrors()){
            model.addAttribute("navUser", sm.getLoggedUser(session));
            model.addAttribute("isToSignup", true);
            return "formUser";
        }
        
        UsuarioEntity userExists = us.getUserByEmail(user.getEmail());
        
        if(userExists == null){
            us.saveUsuario(user);
        }else{
            result.rejectValue("email", "error.user", "o email já está cadastrado.");
            model.addAttribute("navUser", sm.getLoggedUser(session));
            model.addAttribute("isToSignup", true);
            return "formUser";
        }
        
        return "redirect:/usuario/login";
    }
    
    @PostMapping("/loginUser")
    public String loginUser(@ModelAttribute("usuario") UsuarioEntity user, HttpSession session, Model model){
        UsuarioEntity userFound = us.getUserByEmail(user.getEmail());
        
        if(userFound != null && userFound.getEmail().equals(user.getEmail()) && userFound.getPassword().equals(user.getPassword())){
            sm.loginUser(session, userFound);
            
            return "redirect:/";
        }else{
            model.addAttribute("isToSignup", false);
            return "redirect:/usuario/login";
        }
    }
    
    @GetMapping("/logoutUser")
    public String logoutUser(HttpSession session){
        sm.logoutUser(session);
        
        return "redirect:/";
    }
}
