package com.farhad.example.basickafkasample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * Here is a trivial Spring Boot application that demonstrates how to use the ConsumerSeekCallback; it sends 10 records 
 * to the topic; hitting <Enter> in the console causes all partitions to seek to the beginning.
 */
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}



}
