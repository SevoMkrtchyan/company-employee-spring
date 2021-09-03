package com.example.companyemployeespring.service.impl;

import com.example.companyemployeespring.model.Comment;
import com.example.companyemployeespring.model.Employee;
import com.example.companyemployeespring.model.Topic;
import com.example.companyemployeespring.repository.CommentRepository;
import com.example.companyemployeespring.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    public void save(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public List<Comment> findAllByEmployee(Employee employee) {
        try {
            List<Comment> allByEmployee = commentRepository.findAllByEmployee(employee);
            return allByEmployee;
        } catch (NullPointerException e) {
            e.getMessage();
        }
        return null;
    }

    @Override
    public List<Comment> findAllByTopic(Topic topic) {
        try {
            List<Comment> allByTopic = commentRepository.findAllByTopic(topic);
            return allByTopic;
        } catch (NullPointerException e) {
            e.getMessage();
        }
        return null;
    }

    public void delete(int id, Employee employee) {
        List<Comment> allByEmployee = commentRepository.findAllByEmployee(employee);
        if (!allByEmployee.isEmpty()) {
            if (allByEmployee.contains(commentRepository.findById(id).get())) {
                commentRepository.delete(commentRepository.findById(id).get());
            }
        }
    }


}
