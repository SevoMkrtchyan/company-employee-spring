package com.example.companyemployeespring.repository;

import com.example.companyemployeespring.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

    Company findByName(String name);


}
