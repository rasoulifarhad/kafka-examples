package com.farhad.example.basickafkasample.container.normal;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.kafka.core.KafkaTemplate;

import lombok.extern.slf4j.Slf4j;
import static com.farhad.example.basickafkasample.container.normal.KafkaConstants.*;


@Configuration
@Slf4j
public class Boot {
   
    

    @Bean
    @Order(400)
    public ApplicationRunner sendDataToTopic3(KafkaTemplate<String,String> kafkaTemplate) {

        return args -> {

            for (int i = 0; i < 10; i++) {
                log.info("Sending: {} To: {}", DEFAULT_CONTAINER_MESSAGE + i , DEFAULT_CONTAINER_TOPIC);
                kafkaTemplate.send(DEFAULT_CONTAINER_TOPIC,DEFAULT_CONTAINER_MESSAGE + i );
            }
        };


    }


}
