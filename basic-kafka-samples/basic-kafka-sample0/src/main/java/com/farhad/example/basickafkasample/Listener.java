package com.farhad.example.basickafkasample;

import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

import static com.farhad.example.basickafkasample.Configuration.*;

@Component
@Slf4j
public class Listener {

    @KafkaListener(topics = TOPIC ,id = "consumerGroup")
    public void listen(String in,
                                @Header ( KafkaHeaders.RECEIVED_TOPIC ) String topic,
                                @Header ( KafkaHeaders.OFFSET ) long offset) {

        log.info("{} Received from {} @ {}",in,topic,offset);
    }
}
