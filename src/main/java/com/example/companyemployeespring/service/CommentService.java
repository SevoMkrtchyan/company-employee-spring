package com.example.companyemployeespring.service;

import com.example.companyemployeespring.model.Comment;
import com.example.companyemployeespring.model.Employee;
import com.example.companyemployeespring.model.Topic;

import java.util.List;

public interface CommentService {

    void save(Comment comment);

    List<Comment> findAllByEmployee(Employee employee);

    List<Comment> findAllByTopic(Topic topic);

    void delete(int id,Employee employee);

}
