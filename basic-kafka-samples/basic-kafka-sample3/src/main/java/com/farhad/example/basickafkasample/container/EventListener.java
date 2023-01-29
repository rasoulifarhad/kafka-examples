package com.farhad.example.basickafkasample.container;

import org.springframework.boot.autoconfigure.jms.JmsProperties.AcknowledgeMode;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.event.ListenerContainerIdleEvent;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class EventListener {

    @KafkaListener(id = "my-id", topics = "my-topic-3" ,idIsGroup = false)
    public void listen(@Payload String in ) {
        log.info("Received: {} -- {} ", in);
        
    }

    @org.springframework.context.event.EventListener
    public void eventHandler(ListenerContainerIdleEvent event)  {

        log.info("Event Recevied: {}", event);
        
    }
}
