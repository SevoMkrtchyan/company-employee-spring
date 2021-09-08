package com.example.companyemployeespring.controller;

import com.example.companyemployeespring.model.Company;
import com.example.companyemployeespring.security.CurrentUser;
import com.example.companyemployeespring.service.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CompanyController {

    private final CompanyService companyService;


    @GetMapping("/companies")
    public String getCompanies(ModelMap modelMap) {
        modelMap.addAttribute("companies", companyService.findAll());
        log.info("Opened Companies Page at {}", new Date());
        return "companies";
    }

    @GetMapping("/admin/addCompany")
    public String redirectToAddCompany(ModelMap modelMap, @AuthenticationPrincipal CurrentUser currentUser) {
        modelMap.addAttribute("company", new Company());
        log.info("Administrator {} tries to open Add Company Page", currentUser.getEmployee().getName());
        return "addCompany";
    }

    @PostMapping("/admin/addCompany")
    public String addCompany(@ModelAttribute Company company, @AuthenticationPrincipal CurrentUser currentUser) {
        if (company != null) {
            if (company.getName().equals("no_company")) {
                log.error("Administrator {} want's to create company with name {}",
                        currentUser.getEmployee().getName(), company.getName());
                return "redirect:/admin";
            }
            companyService.save(company);
            log.info("Administrator {} has created a new company with name {}",
                    currentUser.getEmployee().getName(), company.getName());
            return "redirect:/admin";
        }
        log.info("Attempt to create a company where data is empty, logged admin is {} request created at {}"
                , currentUser.getEmployee().getName(), new Date());
        return "redirect:/admin";
    }

    @GetMapping("/admin/deleteCompany")
    public String deleteCompanyById(@RequestParam("id") int id) {
        companyService.delete(id);
        log.info("Company with name {} has removed from database at {}"
                , companyService.findById(id).get().getName(), new Date());
        return "redirect:/companies";
    }

    @GetMapping("/singleCompanyPage")
    public String singleCompanyPage(ModelMap modelMap, @RequestParam("id") int id) {
        try {
            Optional<Company> byId = companyService.findById(id);
            modelMap.addAttribute("companyById", byId.get());
        } catch (NullPointerException exception) {
            log.error("Attempt to get Company by id = {} Result is null -> error message {}",
                    id, exception.getMessage());
        }
        return "singleCompany";
    }


}
