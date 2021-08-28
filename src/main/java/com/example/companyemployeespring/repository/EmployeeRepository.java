package com.example.companyemployeespring.repository;

import com.example.companyemployeespring.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    List<Employee> findAllByCompany_Id(int id);

    Optional<Employee> findEmployeeByUsername(String username);

    List<Employee> findEmployeesByCompanyId(int id);
}
