package com.example.companyemployeespring.controller;

import com.example.companyemployeespring.model.Company;
import com.example.companyemployeespring.model.Employee;
import com.example.companyemployeespring.model.Position;
import com.example.companyemployeespring.repository.CompanyRepository;
import com.example.companyemployeespring.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @GetMapping("/employees")
    public String getEmployees(ModelMap modelMap){
        List<Employee> employees = employeeRepository.findAll();
        modelMap.addAttribute("employees",employees);
        return "employees";
    }

    @GetMapping("/addEmployee")
    public String redirectToAddEmployee(ModelMap modelMap){
        modelMap.addAttribute("employee",new Employee());
        modelMap.addAttribute("companies",companyRepository.findAll());
        return "addEmployee";
    }

    @PostMapping("/addEmployee")
    public String addEmployee(@ModelAttribute Employee employee) {
        if (employee != null) {
            if (employee.getPosition() == null){
                employee.setPosition(Position.NO_POSITION_YET);
            }
            employeeRepository.save(employee);
            return "redirect:/employees";
        }
        return "redirect:/employees";
    }

    @GetMapping("/deleteEmployee")
    public String deleteEmployeeById(@RequestParam("id") int id){
        Optional<Employee> employee = employeeRepository.findById(id);
        employeeRepository.delete(employee.get());
        return "redirect:/employees";
    }

}
