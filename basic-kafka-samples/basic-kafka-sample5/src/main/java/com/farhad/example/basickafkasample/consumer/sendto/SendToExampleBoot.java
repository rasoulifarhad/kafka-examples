package com.farhad.example.basickafkasample.consumer.sendto;

import java.util.Collections;
import java.util.stream.IntStream;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;

import lombok.extern.slf4j.Slf4j;

import static com.farhad.example.basickafkasample.consumer.sendto.KafkaConstants.*;


@Configuration
@Slf4j
public class SendToExampleBoot {
    
    @Bean
    @Order(100)
    public ApplicationRunner runner0(
                                    KafkaTemplate<String,String> kafkaTemplate ) {
        return args -> {

            log.info("Start sending runner0....");
            IntStream.range(0, 1)
                      .forEach(value -> 
                                kafkaTemplate.send(new ProducerRecord<String,String>(EXAMPLE_TOPIC_0, 
                                                                                     "foo" + value , 
                  
                                                                                     "bar" + value)));
        };

    }

    @Bean
    @Order(200)
    public ApplicationRunner runner1(
                                    KafkaTemplate<String,String> kafkaTemplate ) {
        return args -> {

            log.info("Start sending runner1 ....");
            IntStream.range(0, 1)
                      .forEach(value -> 
                                kafkaTemplate.send
                                    (new ProducerRecord<String,String>(
                                        EXAMPLE_TOPIC_1, 
                                        0,
                                        "foo" + value , 
                                        "bar" + value,
                                        Collections.singletonList(
                                            new RecordHeader(
                                                KafkaHeaders.REPLY_TOPIC, 
                                                SEND_TO_EXAMPLE_TOPIC_1.getBytes())))));
        };

    }

    @Bean
    @Order(300)
    public ApplicationRunner runner2(
                                    KafkaTemplate<String,String> kafkaTemplate ) {
        return args -> {

            log.info("Start sending runner2 ....");
            IntStream.range(0, 1)
                      .forEach(value -> 
                                kafkaTemplate.send(new ProducerRecord<String,String>(EXAMPLE_TOPIC_2, 
                                                                                     "foo" + value , 
                                                                                     SEND_TO_EXAMPLE_TOPIC_2)));
        };

    }


}
