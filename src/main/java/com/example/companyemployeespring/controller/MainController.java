package com.example.companyemployeespring.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MainController {


    @GetMapping("/")
    public String index(@AuthenticationPrincipal User principal, ModelMap modelMap){
        String username = null;
        if (principal != null){
            username = principal.getUsername();
        }
        modelMap.addAttribute("username",username);
        return "index";
    }


}
