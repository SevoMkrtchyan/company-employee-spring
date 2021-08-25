package com.example.companyemployeespring.controller.advice;

import com.example.companyemployeespring.model.User;
import com.example.companyemployeespring.security.CurrentUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class MainAdvice {

    @ModelAttribute("currentUser")
    public User currentUser(@AuthenticationPrincipal CurrentUser currentUser, ModelMap modelMap) {
        if (currentUser == null) {
            return new User();
        }
        return currentUser.getUser();
    }

}
