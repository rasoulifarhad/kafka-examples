package com.farhad.example.kafka.producerdemo;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

@SpringBootApplication
public class Application {

	private static final String TOPIC = "demo-topic";

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public NewTopic myTopic() {
		return TopicBuilder
					.name(TOPIC)
					.partitions(1)
					.replicas(1)
					.build();
	}

}
