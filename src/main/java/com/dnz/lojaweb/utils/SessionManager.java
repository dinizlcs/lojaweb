package com.dnz.lojaweb.utils;

import com.dnz.lojaweb.model.UsuarioEntity;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

@Component
public class SessionManager {
    public static final String USER_SESSION_KEY = "loggedUser";
    
    public static void loginUser(HttpSession session, UsuarioEntity user){
        session.setAttribute(USER_SESSION_KEY, user);
    }
    
    public static UsuarioEntity getLoggedUser(HttpSession session){
        return (UsuarioEntity) session.getAttribute(USER_SESSION_KEY);
    }
    
    public static void logoutUser(HttpSession session){
        session.removeAttribute(USER_SESSION_KEY);
    }
    
    public static boolean isUserLogged(HttpSession session){
        return session.getAttribute(USER_SESSION_KEY) != null;
    }
}
