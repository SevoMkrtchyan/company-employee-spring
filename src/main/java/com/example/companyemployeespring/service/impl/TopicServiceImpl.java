package com.example.companyemployeespring.service.impl;

import com.example.companyemployeespring.model.Employee;
import com.example.companyemployeespring.model.Topic;
import com.example.companyemployeespring.repository.EmployeeRepository;
import com.example.companyemployeespring.repository.TopicRepository;
import com.example.companyemployeespring.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {

    private final TopicRepository topicRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public void save(Topic topic) {
        topicRepository.save(topic);
    }

    @Override
    public List<Topic> findAllByEmployee_Company_Id(int id) {
        try {
            List<Topic> allByEmployee = topicRepository.findAllByEmployee_Company_Id(id);
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
