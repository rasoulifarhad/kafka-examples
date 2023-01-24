package com.farhad.example.basickafkasample;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import com.fasterxml.jackson.databind.deser.std.StringDeserializer;


public class Configuration {
    
    public static final String TOPIC = "sender-topic";

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String,String> kafkaListenerContainerFactory(
                                ConsumerFactory<String,String> cf ) {
        
        ConcurrentKafkaListenerContainerFactory<String,String>  factory = 
                                            new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(cf);
        return factory ;
    }

    @Bean
    public ConsumerFactory<String,String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerProps());
    }

    private Map<String,Object> consumerProps() {

        Map<String,Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "consumerGroup");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.ALLOW_AUTO_CREATE_TOPICS_CONFIG, "earliest");

        return props;
    }


    @Bean
    public ProducerFactory<String,String> producerFactory() {

        return new DefaultKafkaProducerFactory<>(producerProps());
    }

    private Map<String,Object> producerProps() {

        Map<String,Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.LINGER_MS_CONFIG, 10);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return props;
    }

    @Bean
    public KafkaTemplate<String,String> kafkaTemplate(ProducerFactory<String,String> pf) {

        return new KafkaTemplate<>(pf);

    }

    @Bean
    public KafkaTemplate<String , Integer> intKafkaTemplate(ProducerFactory<String,Integer> pf) {

        return new KafkaTemplate<>(pf,
                    Collections.singletonMap(
                                    ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, 
                                    IntegerSerializer.class));

    }

    @Bean
    public NewTopic senderTopic() {
        return TopicBuilder
                    .name(TOPIC)
                    .partitions(1)
                    .replicas(1)
                    .build();
    }

}
