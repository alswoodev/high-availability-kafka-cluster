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

    public void publish(String topic, String message){
        kafkaTemplate.send(topic, message);
    }
    
    public void publishForStream(String topic, String message) {
        String key = null;
        String value = message;

        if (message.contains(":")) {
            String[] parts = message.split(":", 2);
            key = parts[0];
            value = parts[1];
        }

        kafkaTemplate.send(topic, key, value);
    }

    @KafkaListener(topics=TOPIC , groupId="test")
    public void listener(String message){
        System.out.println(String.format("Sub: %s", message));
    }
}
