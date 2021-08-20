package com.example.companyemployeespring.service;

import com.example.companyemployeespring.model.Employee;
import com.example.companyemployeespring.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> findAll(){
        return employeeRepository.findAll();
    }

    public void save(Employee employee){
        employeeRepository.save(employee);
    }

    public Optional<Employee> findById(int id){

       return employeeRepository.findById(id);
    }

    public List<Employee> findAllByCompany_Id(int id){
        return employeeRepository.findAllByCompany_Id(id);
    }

}
