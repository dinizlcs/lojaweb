package com.dnz.lojaweb.utils;

import com.dnz.lojaweb.model.ProdutoEntity;
import com.dnz.lojaweb.model.UsuarioEntity;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class SessionManager {
    public static final String USER_SESSION_KEY = "loggedUser";
    public static final String CART_SESSION_KEY = "userCart";
    
    // Usuário
    public static void loginUser(HttpSession session, UsuarioEntity user){
        session.setAttribute(USER_SESSION_KEY, user);
    }
    
    public static UsuarioEntity getLoggedUser(HttpSession session){
        return (UsuarioEntity) session.getAttribute(USER_SESSION_KEY);
    }
    
    public static void logoutUser(HttpSession session){
        session.removeAttribute(USER_SESSION_KEY);
        session.removeAttribute(CART_SESSION_KEY);
    }
    
    public static boolean isUserLogged(HttpSession session){
        return session.getAttribute(USER_SESSION_KEY) != null;
    }
    
    // Carrinho
    public static void addToCart(HttpSession session, ProdutoEntity produto){
        List<ProdutoEntity> lstCart = getCart(session);
        
        if(produto.getImage() != null){
            byte[] imgBytes = produto.getImage();
            String base64Image = Base64.getEncoder().encodeToString(imgBytes);
            produto.setBase64Image(base64Image);
        }
        
        if(!lstCart.contains(produto)){
            lstCart.add(produto);
            session.setAttribute(CART_SESSION_KEY, lstCart);
        }else{
            System.out.println("Produto já adicionado.");
        }
    }
    
    public static void removeFromCart(HttpSession session, Integer idToRemove){
        List<ProdutoEntity> lstCart = getCart(session);
        
        for(int i = 0; i < lstCart.size(); i++){
            if(lstCart.get(i).getId().equals(idToRemove)){
                lstCart.remove(i);
                break;
            }
        }
        
        session.setAttribute(CART_SESSION_KEY, lstCart);
    }
    
    public static void clearCart(HttpSession session){
        session.removeAttribute(CART_SESSION_KEY);
    }
    
    public static List<ProdutoEntity> getCart(HttpSession session){
        List<ProdutoEntity> lstCart = (List<ProdutoEntity>) session.getAttribute(CART_SESSION_KEY);
        if(lstCart == null){
            lstCart = new ArrayList();
            session.setAttribute(CART_SESSION_KEY, lstCart);
        }
        
        return lstCart;
    }
}
