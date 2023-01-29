package com.farhad.example.basickafkasample.container;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

@Configuration
public class ConsumerConfig {
    
    // @Bean
    // public ConcurrentMessageListenerContainer<String,String>  concurrentMessageListenerContainer(
    //                                     ConcurrentKafkaListenerContainerFactory<String,String> factory ) {

    //     ConcurrentMessageListenerContainer<String,String> container = factory.createContainer("my-topic-3");

    //     container.getContainerProperties().setGroupId("my-group-3");
    //     container.getContainerProperties().setIdleEventInterval(60_000L);
    //     container.start();
    //     return container ;

    // }
}
