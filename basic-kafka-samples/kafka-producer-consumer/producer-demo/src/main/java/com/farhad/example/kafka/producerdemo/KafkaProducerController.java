package com.farhad.example.kafka.producerdemo;

import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class KafkaProducerController {
    
    private static final int LOOP_COUNT = 100;
    private static final String TOPIC = "demo-topic";
    private AtomicInteger counter = new AtomicInteger(0) ;

    @GetMapping(value="/sendMessages")
    public String sendMessages( ) {

        
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);

        for (int i = 0; i < LOOP_COUNT; i++) {
            int id = counter.incrementAndGet();
            producer.send(new ProducerRecord<String,String>(TOPIC, Integer.toString(id), Integer.toString(id)),
                        (metadata, e) -> {
                            if( e != null ) {
                                e.printStackTrace();
                            } else {
                                log.info("Offset: {}", metadata.offset());
                            }
                        });
        }
        producer.close();
        return "Messages sended";
    }
    
}
