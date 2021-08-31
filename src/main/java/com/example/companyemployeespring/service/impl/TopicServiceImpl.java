package com.example.companyemployeespring.service.impl;

import com.example.companyemployeespring.model.Employee;
import com.example.companyemployeespring.model.Topic;
import com.example.companyemployeespring.repository.TopicRepository;
import com.example.companyemployeespring.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {

    private final TopicRepository topicRepository;

    @Override
    public void save(Topic topic) {
        topicRepository.save(topic);
    }

    @Override
    public List<Topic> findAll() {
        try {
            return topicRepository.findAll();
        } catch (NullPointerException e) {
            e.getMessage();
        }
        return null;
    }

    @Override
    public List<Topic> findAllByEmployee(Employee employee) {
        try {
            List<Topic> allByEmployee = topicRepository.findAllByEmployee(employee);
            return allByEmployee;
        } catch (NullPointerException e) {
            e.getMessage();
        }
        return null;
    }

    @Override
    public Topic findOneById(int id) {
        Optional<Topic> byId = topicRepository.findById(id);
        return byId.orElseGet(Topic::new);
    }

}
