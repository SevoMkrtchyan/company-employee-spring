package com.example.companyemployeespring.controller;

import com.example.companyemployeespring.model.Role;
import com.example.companyemployeespring.model.User;
import com.example.companyemployeespring.security.CurrentUser;
import com.example.companyemployeespring.service.CompanyService;
import com.example.companyemployeespring.service.EmployeeService;
import com.example.companyemployeespring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class AdminController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CompanyService companyService;

    @GetMapping("/admin")
    public String adminPage(ModelMap modelMap){
       modelMap.addAttribute("users",userService.findAll());
       modelMap.addAttribute("employees",employeeService.findAll());
       modelMap.addAttribute("companies",companyService.findAll());
        return "admin";
    }

    @GetMapping("/admin/loginPage")
    public String getLoginPage(){
        return "login";
    }

    @GetMapping("/successLogin")
    public String loginPage(@AuthenticationPrincipal CurrentUser currentUser){
        if (currentUser == null){
            return "redirect:/";
        }
        if (currentUser.getUser().getRole() == Role.ADMIN){
            return "redirect:/admin";
        }
        return "redirect:/user";
    }

    @GetMapping("/register")
    public String forwardRegPage(ModelMap modelMap){
        modelMap.addAttribute("user",new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user){
        Optional<User> userOptional = userService.findByUsername(user.getUsername());
        if (userOptional.isPresent()){
            return  "redirect:/?msg=User Already Exist";
        }
        userService.save(userOptional.get());
        return "redirect:/?msg=User has successfully registered";
    }

}
