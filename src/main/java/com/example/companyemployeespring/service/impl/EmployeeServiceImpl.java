package com.example.companyemployeespring.service.impl;

import com.example.companyemployeespring.model.Employee;
import com.example.companyemployeespring.model.Position;
import com.example.companyemployeespring.repository.EmployeeRepository;
import com.example.companyemployeespring.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public void save(Employee employee) {
        if (employee.getPosition() == null) {
            employee.setPosition(Position.NO_POSITION_YET);
        }
        Optional<Employee> employeeByUsername = employeeRepository.findEmployeeByUsername(employee.getUsername());
        if (!employeeByUsername.isPresent()) {
            employee.setPassword(passwordEncoder.encode(employee.getPassword()));
            employeeRepository.save(employee);
        }

    }

    public Optional<Employee> findById(int id) {
        if (!employeeRepository.findById(id).isPresent()) {
            return null;
        }
        return employeeRepository.findById(id);
    }

    public List<Employee> findAllByCompany_Id(int id) {
        return employeeRepository.findAllByCompany_Id(id);
    }

    public void delete(int id) {
        if (id > 0) {
            Employee byId = findById(id).get();
            employeeRepository.delete(byId);
        }
    }

    public Optional<Employee> findEmployeeByUsername(String username) {
        return employeeRepository.findEmployeeByUsername(username);
    }

    public List<Employee> findEmployeesByCompanyId(int id) {
        return employeeRepository.findAllByCompany_Id(id);
    }


}
