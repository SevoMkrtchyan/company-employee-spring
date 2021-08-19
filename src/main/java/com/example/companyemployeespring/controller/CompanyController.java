package com.example.companyemployeespring.controller;

import com.example.companyemployeespring.model.Company;
import com.example.companyemployeespring.model.Employee;
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
public class CompanyController {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

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
    public String addCompany(@ModelAttribute Company company,ModelMap modelMap) {
        if (company != null) {
            if (company.getName().equals("open_to_work")){
                return "redirect:/companies";
            }
            companyRepository.save(company);
            return "redirect:/companies";
        }
        return "redirect:/companies";
    }

    @GetMapping("/deleteCompany")
    public String deleteCompanyById(@RequestParam("id") int id){
        Optional<Company> company = companyRepository.findById(id);
        List<Employee> allByCompany_id = employeeRepository.findAllByCompany_Id(company.get().getId());
        if (allByCompany_id != null){
            for (Employee employee : allByCompany_id) {
                employee.setCompany(returnDefaultCompany());
            }
        }
        companyRepository.delete(company.get());
        return "redirect:/companies";
    }

    public Company returnDefaultCompany(){
        Company company = companyRepository.findByName("open_to_work");
        if (companyRepository.findByName("open_to_work") == null){
            companyRepository.save(Company.builder().name("open_to_work").size(5).address("anywhere").build());
            return companyRepository.findByName("open_to_work");
        }
        return company;
    }

}
