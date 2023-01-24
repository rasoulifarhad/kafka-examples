package com.farhad.demo.kafka.springbootwithkafka.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class Producer {
    
    private static final String TOPIC = "users";
    
    private final KafkaTemplate<String,String> kafkaTemplate ;

    public void sendMessage(String message) {

        log.info("Producing: {}", message);
        kafkaTemplate.send(TOPIC, message);
    }

    public void sendMessage(String message,String topic) {

        log.info("Producing: {} to Topic: {}", message,topic);
        kafkaTemplate.send(topic, message);
    }

}
