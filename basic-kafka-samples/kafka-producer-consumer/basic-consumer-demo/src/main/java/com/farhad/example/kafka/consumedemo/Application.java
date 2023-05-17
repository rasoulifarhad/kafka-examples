package com.farhad.example.kafka.consumedemo;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class Application {

	private static final String GROUP_ID = "my-group";
    private static final String TOPIC = "demo-topic";

	static String applicationName ;
	
	public static void main(String[] args) {
		
		SpringApplication.run(Application.class, args);
		
		Properties props = new Properties();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		props.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
		props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG,"1000");

		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");

		KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

		consumer.subscribe(Arrays.asList(TOPIC));
		while (true) {
			ConsumerRecords<String, String>  recorde = consumer.poll(Duration.ofMillis(100));
			for (ConsumerRecord<String,String> record : recorde) {
				log.info(String.format("%-10s CONSUMES [%3s,%3s] FROM  [P%-2d,O%-3s]", applicationName,  record.key(), record.value(), record.partition(), record.offset()));
				// log.info(String.format("`{}` Consume from `P{}` at Offset `{}` : [{} , {}]", applicationName, record.partition(),  record.key(), record.value(), record.offset()));
			}
		}
 	}

	 @Bean
	 public NewTopic myTopic(  @Value("${demo-topic.partitions}") int partitions) {
		 return TopicBuilder
					 .name(TOPIC)
					 .partitions(partitions)
					 .replicas(1)
					 .build();
	 }
 
    @Value("${spring.application.name}")
	public void setAppName(String appName) {
		Application.applicationName = appName;
	}


}
