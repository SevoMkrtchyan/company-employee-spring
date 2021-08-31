package com.example.companyemployeespring.controller;

import com.example.companyemployeespring.model.Position;
import com.example.companyemployeespring.security.CurrentUser;
import com.example.companyemployeespring.service.CompanyService;
import com.example.companyemployeespring.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final PasswordEncoder passwordEncoder;
    private final EmployeeService employeeService;
    private final CompanyService companyService;


    @GetMapping("/loginPage")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/successLogin")
    public String loginPage(@AuthenticationPrincipal CurrentUser currentUser) {
        if (currentUser.getEmployee().getPosition().equals(Position.ADMINISTRATOR)) {
            return "redirect:/admin";
        }
        if (currentUser.getEmployee().getPosition().equals(Position.PRESIDENT)){
            return "redirect:/president";
        }
        return "redirect:/loggedEmployee";
    }

    @GetMapping("/admin")
    public String adminPage(ModelMap modelMap) {
        if (!employeeService.findAll().isEmpty()){
            modelMap.addAttribute("employees", employeeService.findAll());
        }
        if (!companyService.findAll().isEmpty()) {
            modelMap.addAttribute("companies", companyService.findAll());
        }
        modelMap.addAttribute("employeesMessage","Employees not found");
        modelMap.addAttribute("companiesMessage","Companies not found");

        return "admin";
    }


}
