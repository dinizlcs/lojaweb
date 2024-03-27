package com.dnz.lojaweb.controller;

import com.dnz.lojaweb.utils.SessionManager;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception e, HttpSession session){
        ModelAndView mv = new ModelAndView("error-page");
        mv.addObject("navUser", SessionManager.getLoggedUser(session));
        return mv;
    }
}
