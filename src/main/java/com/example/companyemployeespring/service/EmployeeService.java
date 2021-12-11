package com.example.companyemployeespring.service;

import com.example.companyemployeespring.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

public interface EmployeeService {

    Page<Employee> findAll(PageRequest pageRequest);

    List<Employee> findAll();

    boolean save(Employee employee,Locale locale);

    Optional<Employee> findById(int id);

    List<Employee> findAllByCompany_Id(int id);

    void delete(int id);

    Optional<Employee> findEmployeeByUsername(String username);

    Employee findEmployeeByEmail(String email);

    void verifyEmail(String email, UUID token);
}
