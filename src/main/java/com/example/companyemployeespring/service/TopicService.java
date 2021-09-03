package com.example.companyemployeespring.service;

import com.example.companyemployeespring.model.Company;
import com.example.companyemployeespring.model.Topic;

import java.util.List;

public interface TopicService {

    void save(Topic topic);

    List<Topic> findAllByEmployee_Company_Id(int id);

    Topic findOneById(int id);

}
