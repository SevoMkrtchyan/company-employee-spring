package com.example.companyemployeespring.service;

import com.example.companyemployeespring.model.Employee;
import com.example.companyemployeespring.model.Topic;

import java.util.List;

public interface TopicService {

    void save(Topic topic);

    List<Topic> findAll();

    List<Topic> findAllByEmployee(Employee employee);

    Topic findOneById(int id);

}
