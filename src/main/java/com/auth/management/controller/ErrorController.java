package com.auth.management.controller;


import com.auth.management.util.SecurityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorController {

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        User currentUser = SecurityUtils.getCurrentUser();
        String user = currentUser!=null? currentUser.getUsername():"";
        model.addAttribute("user",user);
        return "error/404";
    }
}