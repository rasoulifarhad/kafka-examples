package com.farhad.example.basickafkasample.consumer.seek;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import static com.farhad.example.basickafkasample.consumer.seek.KafkaConstants.*;

@Configuration
public class SeekConfig {
    


    @Bean
    public NewTopic seekExampleTopic() {
        return TopicBuilder
                    .name(SEEK_EXAMPLE_TOPIC)
                    .partitions(SEEK_EXAMPLE_TOPIC_PARTITIONS)
                    .replicas((short)1)
                    .build();

    }
}
