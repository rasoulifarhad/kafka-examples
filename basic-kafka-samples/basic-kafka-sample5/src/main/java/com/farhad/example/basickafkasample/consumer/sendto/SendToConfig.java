package com.farhad.example.basickafkasample.consumer.sendto;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import static com.farhad.example.basickafkasample.consumer.sendto.KafkaConstants.*;

@Configuration
public class SendToConfig {
    


    @Bean
    public NewTopic exampleTopic0() {
        return TopicBuilder
                    .name(EXAMPLE_TOPIC_0)
                    .partitions(TOPIC_PARTITIONS)
                    .replicas((short)1)
                    .build();

    }

    @Bean
    public NewTopic exampleTopic1() {
        return TopicBuilder
                    .name(EXAMPLE_TOPIC_1)
                    .partitions(TOPIC_PARTITIONS)
                    .replicas((short)1)
                    .build();

    }
    @Bean
    public NewTopic exampleTopic2() {
        return TopicBuilder
                    .name(EXAMPLE_TOPIC_2)
                    .partitions(TOPIC_PARTITIONS)
                    .replicas((short)1)
                    .build();

    }
    @Bean
    public NewTopic exampleTopic3() {
        return TopicBuilder
                    .name(EXAMPLE_TOPIC_3)
                    .partitions(TOPIC_PARTITIONS)
                    .replicas((short)1)
                    .build();

    }

    @Bean
    public NewTopic sendTokExampleTopic0() {
        return TopicBuilder
                    .name(SEND_TO_EXAMPLE_TOPIC_0)
                    .partitions(SEND_TO_EXAMPLE_TOPIC_PARTITIONS)
                    .replicas((short)1)
                    .build();

    }

    @Bean
    public NewTopic sendTokExampleTopic1() {
        return TopicBuilder
                    .name(SEND_TO_EXAMPLE_TOPIC_1)
                    .partitions(SEND_TO_EXAMPLE_TOPIC_PARTITIONS)
                    .replicas((short)1)
                    .build();

    }
    @Bean
    public NewTopic sendTokExampleTopic2() {
        return TopicBuilder
                    .name(SEND_TO_EXAMPLE_TOPIC_2)
                    .partitions(SEND_TO_EXAMPLE_TOPIC_PARTITIONS)
                    .replicas((short)1)
                    .build();

    }
    @Bean
    public NewTopic sendTokExampleTopic3() {
        return TopicBuilder
                    .name(SEND_TO_EXAMPLE_TOPIC_3)
                    .partitions(SEND_TO_EXAMPLE_TOPIC_PARTITIONS)
                    .replicas((short)1)
                    .build();

    }
}
