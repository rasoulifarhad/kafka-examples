package com.farhad.example.producercloudstream.kafka.alert;

import lombok.Value;

@Value
public class Alert {

    String id;
    Integer level;
    String message;
}
