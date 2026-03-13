package com.example.kafka.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class BasicKafkaService {
    private final String TOPIC = "test";

    private KafkaTemplate<String,Object> kafkaTemplate;

    public BasicKafkaService(KafkaTemplate<String,Object> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }
    
    public void publish(String message){
        kafkaTemplate.send(TOPIC, message);
    }


    @KafkaListener(topics=TOPIC , groupId="test")
    public void listener(String message){
        System.out.println(String.format("Sub: %s", message));
    }
}
