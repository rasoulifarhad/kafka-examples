package com.farhad.example.producerkafka.kafka.alert;

import lombok.Value;

@Value
public class Alert {

    String id;
    Integer level;
    String message;
}
