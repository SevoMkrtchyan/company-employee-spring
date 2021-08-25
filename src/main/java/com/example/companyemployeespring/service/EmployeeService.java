package com.example.companyemployeespring.service;

import com.example.companyemployeespring.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    List<Employee> findAll();

    void save(Employee employee);

    Optional<Employee> findById(int id);

    List<Employee> findAllByCompany_Id(int id);

    void delete(int id);

    Optional<Employee> findEmployeeByUsername(String username);

}
