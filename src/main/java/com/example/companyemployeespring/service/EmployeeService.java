package com.example.companyemployeespring.service;

import com.example.companyemployeespring.model.Employee;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

public interface EmployeeService {

    List<Employee> findAll();

    void save(Employee employee,Locale locale);

    Optional<Employee> findById(int id);

    List<Employee> findAllByCompany_Id(int id);

    void delete(int id);

    Optional<Employee> findEmployeeByUsername(String username);

    Employee findEmployeeByEmail(String email);

    void verifyEmail(String email, UUID token);
}
