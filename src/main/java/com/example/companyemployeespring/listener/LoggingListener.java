package com.example.companyemployeespring.listener;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class LoggingListener {


    @Value(value = "kafka.groupId")
    private String groupId;
//    @RabbitListener(queues = RabbitMQConfig.QUEUE)
//    public void consumeMessageFromQueue(Object message) {
//
//        System.out.println("Message recieved from queue : " + message);
//    }

    @KafkaListener(topics = "topicCustom", groupId = "foo")
    public void listenGroupFoo(String message) {
        System.out.println("Received Message in group foo: " + message);
    }
}
