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
    public void getMethodName(@RequestParam("message") String message) {
        basicKafkaService.publish(message);
    }
}
