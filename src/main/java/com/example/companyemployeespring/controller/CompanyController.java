package com.example.companyemployeespring.controller;

import com.example.companyemployeespring.model.Company;
import com.example.companyemployeespring.service.CompanyService;
import com.example.companyemployeespring.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;
    private final EmployeeService employeeService;

    @GetMapping("/companies")
    public String getCompanies(ModelMap modelMap) {
        modelMap.addAttribute("companies", companyService.findAll());
        return "companies";
    }

    @GetMapping("/addCompany")
    public String redirectToAddCompany(ModelMap modelMap) {
        modelMap.addAttribute("company", new Company());
        return "addCompany";
    }

    @PostMapping("/addCompany")
    public String addCompany(@ModelAttribute Company company, ModelMap modelMap) {
        if (company != null) {
            if (company.getName().equals("no_company")) {
                return "redirect:/companies";
            }
            companyService.save(company);
            return "redirect:/companies";
        }
        return "redirect:/companies";
    }

    @GetMapping("/deleteCompany")
    public String deleteCompanyById(@RequestParam("id") int id) {
        companyService.delete(id);
        return "redirect:/companies";
    }

    public Company returnDefaultCompany() {
        if (companyService.findByName("no_company") == null) {
            companyService.save(Company.builder().name("no_company").size(5).address("no_company").build());
            return companyService.findByName("no_company");
        }
        return companyService.findByName("no_company");
    }

}
