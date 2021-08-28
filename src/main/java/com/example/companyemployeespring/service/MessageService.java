package com.example.companyemployeespring.service;

import com.example.companyemployeespring.model.Message;

import java.util.List;

public interface MessageService {

    void save(Message message);

    List<Message> findMessagesByFromIdAndToId(int fromId, int toId);

}
