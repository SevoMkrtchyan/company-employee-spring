package com.example.companyemployeespring.controller;

import com.example.companyemployeespring.model.Company;
import com.example.companyemployeespring.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class CompanyController {

    @Autowired
    private CompanyRepository companyRepository;

    @GetMapping("/companies")
    public String getCompanies(ModelMap modelMap){
        modelMap.addAttribute("companies",companyRepository.findAll());
        return "companies";
    }

    @GetMapping("/addCompany")
    public String redirectToAddCompany(ModelMap modelMap){
        modelMap.addAttribute("company",new Company());
        return "addCompany";
    }

    @PostMapping("/addCompany")
    public String addCompany(@ModelAttribute Company company) {
        if (company != null) {
            companyRepository.save(company);
            return "redirect:/companies";
        }
        return "redirect:/companies";
    }

    @GetMapping("/deleteCompany")
    public String deleteCompanyById(@RequestParam("id") int id){
        Optional<Company> company = companyRepository.findById(id);
        companyRepository.delete(company.get());
        return "redirect:/companies";
    }

}
