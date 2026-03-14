package com.example.kafka.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.kafka.service.BasicKafkaService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("kafka")
public class KafkaController {

    private BasicKafkaService basicKafkaService;

    public KafkaController(BasicKafkaService basicKafkaService){
        this.basicKafkaService = basicKafkaService;
    }

    @GetMapping
    public void publish(@RequestParam("topic") String topic,@RequestParam("message") String message) {
        basicKafkaService.publish(topic, message);
    }

    // The message should be provided in "key:value" format
    @GetMapping("/stream")
    public void streamPublish(@RequestParam("topic") String topic,@RequestParam("message") String message) {
        basicKafkaService.publish(topic, message);
    }
}
