package com.example.companyemployeespring.service;

import com.example.companyemployeespring.model.Company;
import com.example.companyemployeespring.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public List<Company> findAll(){ return companyRepository.findAll();}

    public void save(Company company){ companyRepository.save(company);}

    public Optional<Company> findById(int id){
        return companyRepository.findById(id);
    }

    public void delete(Company company){companyRepository.delete(company);}

    public Company findByName(String name){
        return companyRepository.findByName(name);
    }

}
