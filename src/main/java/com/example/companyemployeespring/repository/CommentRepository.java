package com.example.companyemployeespring.repository;

import com.example.companyemployeespring.model.Comment;
import com.example.companyemployeespring.model.Employee;
import com.example.companyemployeespring.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findAllByEmployee(Employee employee);

    List<Comment> findAllByTopic(Topic topic);

}
