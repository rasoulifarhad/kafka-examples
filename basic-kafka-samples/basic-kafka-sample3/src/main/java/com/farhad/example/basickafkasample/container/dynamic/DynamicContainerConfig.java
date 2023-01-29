package com.farhad.example.basickafkasample.container.dynamic;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import static com.farhad.example.basickafkasample.container.dynamic.KafkaConstants.*;


@Configuration
public class DynamicContainerConfig {
    

    @Bean
    public NewTopic dcTopic() {
        return TopicBuilder
                    .name(DYNAMIC_CONTAINER_TOPIC)
                    .partitions(1)
                    .replicas(1)
                    .build();
    }

    @Bean
    public NewTopic defDcTopic() {
        return TopicBuilder
                    .name(DEFAULT_DYNAMIC_CONTAINER_TOPIC)
                    .partitions(1)
                    .replicas(1)
                    .build();
    }

}
