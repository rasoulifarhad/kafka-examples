package com.farhad.example.basickafkasample.container.dynamic;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.MessageListener;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyListener implements MessageListener<String,String> {

    @Override
    public void onMessage(ConsumerRecord<String, String> consumerRecord) {
        log.info("Received: {} From: {} ",consumerRecord.value(), consumerRecord.topic());
        
    }
    
}
