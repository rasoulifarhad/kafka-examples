package com.farhad.example.basickafkasample.consumer.seek.tolast;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class SeekToLastOnIdleConfig {
    private static final String TOPIC = "seek-on-idle-topic";
    @Bean
    public NewTopic seekOnIdleTopic() {
        return TopicBuilder
                    .name(TOPIC)
                    .partitions(1)
                    .replicas((short)1)
                    .build();

    }

}
