package com.farhad.example.basickafkasample.container.normal;

import org.springframework.boot.autoconfigure.jms.JmsProperties.AcknowledgeMode;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.event.ListenerContainerIdleEvent;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import static com.farhad.example.basickafkasample.container.normal.KafkaConstants.*;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class EventListener {

    @KafkaListener(id = "my-id", topics = DEFAULT_CONTAINER_TOPIC ,idIsGroup = false)
    public void listen(@Payload String in ,@Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        log.info("EventListener Received: {} From: {}", in,topic);
        
    }

    // @org.springframework.context.event.EventListener(condition = "event.listenerId.startWith('my-id-')")
    @org.springframework.context.event.EventListener
    public void eventHandler(ListenerContainerIdleEvent event)  {

        log.info("EventHandler Recevied: {}", event);
        
    }
}
