package com.example.companyemployeespring.service;

import com.example.companyemployeespring.model.Company;

import java.util.List;
import java.util.Optional;

public interface CompanyService {

    List<Company> findAll();

    void save(Company company);

    Optional<Company> findById(int id);

    void delete(int id);

    Company findByName(String name);

    Company returnDefaultCompany();


}
