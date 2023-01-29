package com.farhad.example.basickafkasample.container;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.kafka.core.KafkaTemplate;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class Boot {
   
    

    @Bean
    @Order(400)
    public ApplicationRunner sendDataToTopic3(KafkaTemplate<String,String> kafkaTemplate) {

        return args -> {

            for (int i = 0; i < 10; i++) {
                log.info("Sending: {} ", "Message # " + i);
                kafkaTemplate.send("my-topic-3","Message # " + i );
            }
        };


    }


}
