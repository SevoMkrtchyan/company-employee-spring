package com.example.companyemployeespring.controller;

import com.example.companyemployeespring.security.CurrentUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;


@Controller
public class MainController {


    @GetMapping("/")
    public String index(@AuthenticationPrincipal CurrentUser currentUser, ModelMap modelMap){
        String username = null;
        String role = null;
        if (currentUser != null){
            username = currentUser.getUsername();
            role = currentUser.getUser().getRole().name();
        }
        modelMap.addAttribute("username",username);
        modelMap.addAttribute("role",role);
        return "index";
    }


}
