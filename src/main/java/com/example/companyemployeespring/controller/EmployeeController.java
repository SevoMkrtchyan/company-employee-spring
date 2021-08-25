package com.example.companyemployeespring.controller;

import com.example.companyemployeespring.model.Employee;
import com.example.companyemployeespring.model.Position;
import com.example.companyemployeespring.service.CompanyService;
import com.example.companyemployeespring.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final CompanyService companyService;

    @GetMapping("/employees")
    public String getEmployees(ModelMap modelMap) {
        List<Employee> employees = employeeService.findAll();
        modelMap.addAttribute("employees", employees);
        return "employees";
    }

    @GetMapping("/addEmployee")
    public String redirectToAddEmployee(ModelMap modelMap) {
        modelMap.addAttribute("employee", new Employee());
        modelMap.addAttribute("companies", companyService.findAll());
        return "addEmployee";
    }

    @PostMapping("/addEmployee")
    public String addEmployee(@ModelAttribute Employee employee) {
        if (employee != null) {
            if (employee.getPosition() == null) {
                employee.setPosition(Position.NO_POSITION_YET);
            }
            employeeService.save(employee);
            return "redirect:/employees";
        }
        return "redirect:/employees";
    }

    @GetMapping("/deleteEmployee")
    public String deleteEmployeeById(@RequestParam("id") int id) {
        employeeService.delete(id);
        return "redirect:/employees";
    }

}
