package com.farhad.example.producercloudstream.kafka.alert;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class AlertEventProducer {


    private final StreamBridge streamBridge;

    public void send(Alert alert) {
        
        log.info("Sending Alert '{}' to binding '{}'", alert, "alert-out-0");

        Message<Alert> message = MessageBuilder
                                        .withPayload(alert)
                                        .setHeader("partitionKey", alert.getId())
                                        .build();

        streamBridge.send("alert-out-0", message);
    }
}
