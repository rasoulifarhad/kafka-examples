package com.farhad.example.basickafkasample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.retry.annotation.Backoff;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@RetryableTopic(attempts = "5",backoff = @Backoff(delay = 2_000,maxDelay = 10_000,multiplier = 2))
	@KafkaListener(id = "fooGroup" , topics = "what-topic")
	public void listen(String in ,
								@Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
								@Header(KafkaHeaders.OFFSET) long offset) {

	   log.info("Received: {} from {} @ {}", in,topic,offset); 
	   if(in.startsWith("fail")) {
			throw new RuntimeException("failed");
	   }

	}

	@DltHandler
	public void listenDlq(String in,
							@Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
							@Header(KafkaHeaders.OFFSET) long offset) {

	log.info("DLT Received: {} from {} @ {}", in,topic,offset); 
		

	}
}
