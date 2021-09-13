package com.example.companyemployeespring.controller;

import com.example.companyemployeespring.model.Position;
import com.example.companyemployeespring.security.CurrentUser;
import com.example.companyemployeespring.service.CompanyService;
import com.example.companyemployeespring.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final EmployeeService employeeService;
    private final CompanyService companyService;


    @GetMapping("/loginPage")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/successLogin")
    public String loginPage(@AuthenticationPrincipal CurrentUser currentUser) {
        if (currentUser.getEmployee().getPosition().equals(Position.ADMINISTRATOR)) {
            log.info("Administrator {} has successfully logged in ", currentUser.getEmployee().getName());
            return "redirect:/admin";
        }
        if (currentUser.getEmployee().getPosition().equals(Position.PRESIDENT)) {
            log.info("President {} of company {} has successfully logged in, President id = {}",
                    currentUser.getEmployee().getName(), currentUser.getEmployee().getCompany().getName(), currentUser.getEmployee().getId());
            return "redirect:/president";
        }
        log.info("Employee {} has successfully logged in, Employee id = {}",
                currentUser.getEmployee().getName(), currentUser.getEmployee().getId());
        return "redirect:/loggedEmployee";
    }

    @GetMapping("/admin")
    public String adminPage(ModelMap modelMap, @AuthenticationPrincipal CurrentUser currentUser) {
        if (!employeeService.findAll().isEmpty()) {
            modelMap.addAttribute("employees", employeeService.findAll());
        }
        if (!companyService.findAll().isEmpty()) {
            modelMap.addAttribute("companies", companyService.findAll());
        }
        modelMap.addAttribute("employeesMessage", "Employees not found");
        modelMap.addAttribute("companiesMessage", "Companies not found");
        log.info("Attempt to open /admin page, logged User id is {} ", currentUser.getEmployee().getId());
        return "admin";
    }

}
