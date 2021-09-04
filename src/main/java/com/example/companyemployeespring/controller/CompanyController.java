package com.example.companyemployeespring.controller;

import com.example.companyemployeespring.model.Company;
import com.example.companyemployeespring.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping("/companies")
    public String getCompanies(ModelMap modelMap) {
        modelMap.addAttribute("companies", companyService.findAll());
        return "companies";
    }

    @GetMapping("/admin/addCompany")
    public String redirectToAddCompany(ModelMap modelMap) {
        modelMap.addAttribute("company", new Company());
        return "addCompany";
    }

    @PostMapping("/admin/addCompany")
    public String addCompany(@ModelAttribute Company company, ModelMap modelMap) {
        if (company != null) {
            if (company.getName().equals("no_company")) {
                return "redirect:/admin";
            }
            companyService.save(company);
            return "redirect:/admin";
        }
        return "redirect:/admin";
    }

    @GetMapping("/admin/deleteCompany")
    public String deleteCompanyById(@RequestParam("id") int id) {
        companyService.delete(id);
        return "redirect:/companies";
    }

    @GetMapping("/singleCompanyPage")
    public String singleCompanyPage(ModelMap modelMap, @RequestParam("id") int id) {
        try {
            Optional<Company> byId = companyService.findById(id);
            modelMap.addAttribute("companyById", byId.get());
        } catch (NullPointerException exception) {
            exception.getMessage();
        }
        return "singleCompany";
    }


}
