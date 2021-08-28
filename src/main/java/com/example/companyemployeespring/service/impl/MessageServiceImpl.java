package com.example.companyemployeespring.service.impl;

import com.example.companyemployeespring.model.Message;
import com.example.companyemployeespring.repository.MessageRepository;
import com.example.companyemployeespring.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    public void save(Message message) {
        messageRepository.save(message);
    }

    public List<Message> findMessagesByFromIdAndToId(int fromId, int toId) {
        List<Message> messageByFromIdAndToId = messageRepository.findMessagesByFrom_IdAndTo_Id(fromId, toId);
        List<Message> messageByToIdAndToFrom = messageRepository.findMessagesByFrom_IdAndTo_Id(toId, fromId);
        List<Message> all = new LinkedList<>();
        for (Message message : messageByFromIdAndToId) {
            if (message.getFrom().getId() == fromId && message.getTo().getId() == toId) {
                for (Message message1 : messageByToIdAndToFrom) {
                    if (message1.getFrom().getId() == toId && message1.getTo().getId() == fromId){
                        all.add(message1);
                    }
                }
                all.add(message);
            }
        }
        if (!all.isEmpty()) {
            Set<Message> set = new HashSet<>(all);
            all.clear();
            all.addAll(set);
            Collections.sort(all, new Comparator<Message>() {
                @Override
                public int compare(Message message, Message t1) {
                    return message.getTimestamp().compareTo(t1.getTimestamp());
                }
            });
            return all;
        }
        return null;
    }


}
