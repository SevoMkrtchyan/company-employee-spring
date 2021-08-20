package com.example.companyemployeespring.controller;

import com.example.companyemployeespring.model.Company;
import com.example.companyemployeespring.model.Employee;
import com.example.companyemployeespring.model.Position;
import com.example.companyemployeespring.service.CompanyService;
import com.example.companyemployeespring.service.EmployeeService;
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
    private CompanyService companyService;

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/companies")
    public String getCompanies(ModelMap modelMap){
        modelMap.addAttribute("companies",companyService.findAll());
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
            companyService.save(company);
            return "redirect:/companies";
        }
        return "redirect:/companies";
    }

    @GetMapping("/deleteCompany")
    public String deleteCompanyById(@RequestParam("id") int id){
        Optional<Company> company = companyService.findById(id);
        List<Employee> allByCompany_id = employeeService.findAllByCompany_Id(company.get().getId());
        if (allByCompany_id != null){
            for (Employee employee : allByCompany_id) {
                employee.setPosition(Position.OPEN_TO_WORK);
                employee.setCompany(returnDefaultCompany());
            }
        }
        companyService.delete(company.get());
        return "redirect:/companies";
    }

    public Company returnDefaultCompany(){
        if (companyService.findByName("no_company") == null){
            companyService.save(Company.builder().name("no_company").size(5).address("no_company").build());
            return companyService.findByName("no_company");
        }
        return companyService.findByName("no_company");
    }

}
