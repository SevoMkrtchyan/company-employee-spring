package com.example.companyemployeespring.security;

import com.example.companyemployeespring.model.Employee;
import org.springframework.security.core.authority.AuthorityUtils;


public class CurrentUser extends org.springframework.security.core.userdetails.User {

    private Employee employee;

    public CurrentUser(Employee employee) {
        super(employee.getUsername(), employee.getPassword(), AuthorityUtils.createAuthorityList(employee.getPosition().name()));
        this.employee = employee;
    }

    public Employee getEmployee() {
        return employee;
    }
}
