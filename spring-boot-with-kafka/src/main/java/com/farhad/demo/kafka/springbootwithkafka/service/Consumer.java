package com.farhad.demo.kafka.springbootwithkafka.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.adapter.ConsumerRecordMetadata;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j    
public class Consumer {
    
    @KafkaListener(groupId = "group_id",topics = "users",clientIdPrefix = "base_consumer")
    public void consume(String message ,ConsumerRecordMetadata metadata) {

        log.info("Recorde: {} receicved from topic {}:{} @ {} ", message, metadata.topic(),metadata.partition(),metadata.offset());
    }

    
}
