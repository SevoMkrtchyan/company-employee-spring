package com.example.companyemployeespring.repository;

import com.example.companyemployeespring.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {

    List<Message> findMessageByFromIdAndToId(int fromId,int toId);
}
