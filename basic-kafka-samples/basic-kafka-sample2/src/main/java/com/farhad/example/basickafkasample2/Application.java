package com.farhad.example.basickafkasample2;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.retry.annotation.Backoff;

import com.farhad.example.basickafkasample2.common.Foo2;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class Application {

	private final TaskExecutor exec = new SimpleAsyncTaskExecutor();

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@KafkaListener(id = "fooGroup" , topics = "foo-what-topic")
	public void listen(Foo2 foo ,
								@Header(KafkaHeaders.RECEIVED_TOPIC) String topic , 
								@Header(KafkaHeaders.OFFSET) long offset) {

	   log.info("Received: {} from {} @ {}",foo); 
	   if(foo.getFoo().startsWith("fail")) {
			throw new RuntimeException("failed");
	   }
	   exec.execute(() -> System.out.println("Hi Enter to terminate....."));

	}

	@KafkaListener(id = "dltGroup",topics = "foo-what-topic-dlt")
	public void listenDlq(byte[] in ,
		@Header(KafkaHeaders.RECEIVED_TOPIC) String topic , 
		@Header(KafkaHeaders.OFFSET) long offset) {

		log.info("DLT Received: {} from {} @ {}",new String( in),topic,offset); 

		exec.execute(() -> System.out.println("Hi Enter to terminate....."));
		

	}

	@Bean
	public NewTopic topic() {
		return new NewTopic("foo-what-topic", 1,(short) 1);
	}

	@Bean
	public NewTopic dlt() {
		return new NewTopic("foo-what-topic-dlt", 1,(short) 1);
	}

	@Bean
	public ApplicationRunner runner() {
		return args ->  {
			System.out.println("Hi Enter to terminate ....");		
			System.in.read();
		};
	}

}
