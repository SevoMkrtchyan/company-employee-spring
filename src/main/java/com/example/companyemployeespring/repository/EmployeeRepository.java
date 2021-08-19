package com.example.companyemployeespring.repository;

import com.example.companyemployeespring.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee,Integer> {


    void deleteAllByCompany_Id(int id);

    List<Employee> findAllByCompany_Id(int id);
}
