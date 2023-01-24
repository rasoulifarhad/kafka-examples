package com.farhad.example.kafka.cloudstream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.handler.annotation.SendTo;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableBinding(Processor.class)
@Slf4j
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@StreamListener(Processor.INPUT)
    @SendTo(Processor.OUTPUT)
    public String process(String message) {
        log.info("Processor received: {} ", message);
        String uppercased =  message.toUpperCase();
        log.info("Processor upercase:  {} to {} ", message,uppercased);
        return uppercased;
    }

}
