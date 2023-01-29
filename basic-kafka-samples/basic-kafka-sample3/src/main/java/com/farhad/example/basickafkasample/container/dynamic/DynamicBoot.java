package com.farhad.example.basickafkasample.container.dynamic;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class DynamicBoot {
    


    @Bean
    @Order(100)
    public ApplicationRunner createContainer$MyListener(ConcurrentKafkaListenerContainerFactory<String,String> factory) {

        return args -> {
            ConcurrentMessageListenerContainer<String,String> container =  createContainer(factory, "my-topic", "my-group");
        };


    }

    private ConcurrentMessageListenerContainer<String,String>  createContainer(
                                        ConcurrentKafkaListenerContainerFactory<String,String> factory ,
                                        String topic,
                                        String  group) {

        ConcurrentMessageListenerContainer<String,String> container = factory.createContainer(topic);

        container.getContainerProperties().setMessageListener(new MyListener());
        container.getContainerProperties().setGroupId(group);
        container.getContainerProperties().setIdleEventInterval(60_000L);
        container.setBeanName(group);
        container.start();
        return container ;

    }


    @Bean
    @Order(200)
    public ApplicationRunner sendData(KafkaTemplate<String,String> kafkaTemplate) {

        return args -> {

            for (int i = 0; i < 10; i++) {
                log.info("Sending: {} ", "Message # " + i);
                kafkaTemplate.send("my-topic","Message # " + i );
            }
        };


    }


}
