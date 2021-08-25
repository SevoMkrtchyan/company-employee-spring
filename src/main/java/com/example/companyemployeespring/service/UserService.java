package com.example.companyemployeespring.service;

import com.example.companyemployeespring.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> findAll();

    void save(User user);

    Optional<User> findByUsername(String username);

}
