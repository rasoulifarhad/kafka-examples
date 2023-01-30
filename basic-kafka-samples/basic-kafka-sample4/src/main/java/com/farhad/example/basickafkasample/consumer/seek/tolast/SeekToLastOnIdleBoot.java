package com.farhad.example.basickafkasample.consumer.seek.tolast;

import java.util.stream.IntStream;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class SeekToLastOnIdleBoot {
    
    private static final String TOPIC = "seek-on-idle-topic";


    public ApplicationRunner runner(SeekToLastOnIdleListener seekToLastOnIdleListener ,
                                    KafkaTemplate<String,String> kafkaTemplate ) {

        return args -> {

            log.info("Start sending to {} ....",TOPIC);
            IntStream.range(0, 10)
                      .forEach(value -> 
                                kafkaTemplate.send(new ProducerRecord<String,String>(TOPIC, 
                                                                                     "foo" + value , 
                                                                                     "bar" + value)));
                    
        };

    }
}
