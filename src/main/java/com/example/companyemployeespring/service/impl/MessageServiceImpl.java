package com.example.companyemployeespring.service.impl;

import com.example.companyemployeespring.model.Message;
import com.example.companyemployeespring.repository.MessageRepository;
import com.example.companyemployeespring.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    public List<Message> findMessagesByFromIdAndToId(int fromId,int toId){
        List<Message> messageByFromIdAndToId = messageRepository.findMessageByFromIdAndToId(fromId, toId);
        if (!messageByFromIdAndToId.isEmpty()){
         return messageByFromIdAndToId;
        }
        return null;
    }

    public void save(Message message){
        messageRepository.save(message);
    }


}
