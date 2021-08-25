package com.example.companyemployeespring.controller;

import com.example.companyemployeespring.model.Employee;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

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
        if (currentUser == null) {
            return "redirect:/";
        }
        return "redirect:/loggedEmployee";
    }

    @GetMapping("/register")
    public String forwardRegPage(ModelMap modelMap) {
        modelMap.addAttribute("employee", new Employee());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute Employee employee) {
        Optional<Employee> userOptional = employeeService.findEmployeeByUsername(employee.getUsername());
        if (userOptional.isPresent()) {
            return "redirect:/?msg=Employee Already Exist";
        }
        employee.setPosition(Position.NO_POSITION_YET);
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        employeeService.save(employee);
        return "redirect:/admin?msg=Employee has successfully registered";
    }

}
