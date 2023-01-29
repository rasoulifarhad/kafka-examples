package com.farhad.example.basickafkasample.container.normal;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
// import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.config.TopicBuilder;
// import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import static com.farhad.example.basickafkasample.container.normal.KafkaConstants.*;


@Configuration
public class ContainerConfig {
    
    // @Bean
    // public ConcurrentMessageListenerContainer<String,String>  concurrentMessageListenerContainer(
    //                                     ConcurrentKafkaListenerContainerFactory<String,String> factory ) {

    //     ConcurrentMessageListenerContainer<String,String> container = factory.createContainer("my-topic-3");

    //     container.getContainerProperties().setGroupId("my-group-3");
    //     container.getContainerProperties().setIdleEventInterval(60_000L);
    //     container.start();
    //     return container ;

    // }

    @Bean
    public NewTopic defaultTopic() {
        return TopicBuilder
                    .name(DEFAULT_CONTAINER_TOPIC)
                    .partitions(1)
                    .replicas(1)
                    .build();
    }

    @Bean
    public NewTopic containerTopic() {
        return TopicBuilder
                    .name(CONTAINER_TOPIC)
                    .partitions(1)
                    .replicas(1)
                    .build();
    }

}
