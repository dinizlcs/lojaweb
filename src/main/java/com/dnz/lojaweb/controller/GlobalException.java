package com.dnz.lojaweb.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception e){
        ModelAndView mv = new ModelAndView("error-page");
        return mv;
    }
}
