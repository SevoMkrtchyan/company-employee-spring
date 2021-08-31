package com.example.companyemployeespring.repository;

import com.example.companyemployeespring.model.Employee;
import com.example.companyemployeespring.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TopicRepository extends JpaRepository<Topic, Integer> {

    List<Topic> findAllByEmployee(Employee employee);

}
