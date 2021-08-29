package com.example.companyemployeespring.service.impl;

import com.example.companyemployeespring.model.Company;
import com.example.companyemployeespring.model.Employee;
import com.example.companyemployeespring.model.Position;
import com.example.companyemployeespring.repository.CompanyRepository;
import com.example.companyemployeespring.service.CompanyService;
import com.example.companyemployeespring.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final EmployeeService employeeService;

    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    public void save(Company company) {
        companyRepository.save(company);
    }

    public Optional<Company> findById(int id) {
        if (!companyRepository.findById(id).isPresent()) {
            return null;
        }
        return companyRepository.findById(id);
    }

    public void delete(int id) {
        Company company = findById(id).get();
        List<Employee> allByCompany_id = employeeService.findAllByCompany_Id(company.getId());
        if (allByCompany_id != null) {
            for (Employee employee : allByCompany_id) {
                employee.setPosition(Position.OPEN_TO_WORK);
                employee.setCompany(companyRepository.findByName("no_company"));
            }
        }
        companyRepository.delete(company);
    }

    public Company findByName(String name) {
        return companyRepository.findByName(name);
    }


}
