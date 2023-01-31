package com.farhad.example.basickafkasample.consumer.sendto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;
import java.util.stream.IntStream;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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
    @ConditionalOnProperty( value="runner0.enabled", 
                            havingValue = "true", 
                            matchIfMissing = true)
    public ApplicationRunner runner0(
                                    KafkaTemplate<String,String> kafkaTemplate ) {
        return args -> {

            log.info("Start sending runner0....");
            IntStream.range(0, 1)
                      .forEach(value -> 
                                kafkaTemplate.send(new ProducerRecord<String,String>(EXAMPLE_TOPIC_0, 
                                                                                     "foo0-" + value , 
                                                                                     "bar0-" + value)));
        };

    }

    @Bean
    @Order(200)
    @ConditionalOnProperty( value="runner1.enabled", 
                            havingValue = "true", 
                            matchIfMissing = true)
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
                                        "foo1-" + value , 
                                        "bar1-" + value,
                                        Collections.singletonList(
                                            new RecordHeader(
                                                KafkaHeaders.REPLY_TOPIC, 
                                                SEND_TO_EXAMPLE_TOPIC_1.getBytes())))));
        };

    }

    @Bean
    @Order(300)
    @ConditionalOnProperty( value="runner2.enabled", 
                            havingValue = "true", 
                            matchIfMissing = true)

    public ApplicationRunner runner2(
                                    KafkaTemplate<String,String> kafkaTemplate ) {
        return args -> {

            log.info("Start sending runner2 ....");
            IntStream.range(0, 1)
                      .forEach(value -> 
                                kafkaTemplate.send(new ProducerRecord<String,String>(EXAMPLE_TOPIC_2, 
                                                                                     "foo2-" + value , 
                                                                                     SEND_TO_EXAMPLE_TOPIC_2)));
        };

    }

    @Bean
    @Order(400)
    @ConditionalOnProperty( value="runner3.enabled", 
                            havingValue = "true", 
                            matchIfMissing = true)

    public ApplicationRunner runner3(
                                    KafkaTemplate<String,String> kafkaTemplate ) {
        return args -> {

            
            log.info("Start sending runner3 ....");

            IntStream.range(0, 1)
                .forEach(value -> 
                      kafkaTemplate.send
                          (new ProducerRecord<String,String>(
                              EXAMPLE_TOPIC_3, 
                              0,
                              "foo3-" + value , 
                              "bar3-" + value,
                                Collections.unmodifiableList(
                                    Arrays.asList(
                                        new RecordHeader(KafkaHeaders.REPLY_TOPIC, SEND_TO_EXAMPLE_TOPIC_3.getBytes()),
                                        new RecordHeader(KafkaHeaders.CORRELATION_ID, UUID.randomUUID().toString().getBytes())
                                    )
                          
                                ))));


        };

    }



}
