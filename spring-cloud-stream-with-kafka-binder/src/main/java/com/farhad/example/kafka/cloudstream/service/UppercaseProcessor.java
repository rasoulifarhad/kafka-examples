package com.farhad.example.kafka.cloudstream.service;


import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.SendTo;

/**
 * This is a Spring Cloud Stream Processor application that consumes messages from an input and 
 * produces messages to an output.
 * 
 * @StreamListener, which is provided by Spring Cloud Stream to receive messages from a Kafka topic. 
 * @SendTo, which is a convenient annotation for sending messages to an output destination. 
 * 
 * “How is this application communicating with Kafka?” 
 * 
 * The answer is: 
 *                Inbound and outbound topics are configured by using one of the many configuration 
 *                options supported by Spring Boot. 
 * In application.yml:
 *  
 *   Here is the configuration for input and output destinations:
 * 
 *      spring.cloud.stream.bindings:
 *        input:
 *          destination: topic1
 *        output:
 *          destination: topic2
 * 
 *    Spring Cloud Stream maps the input to topic1 and the output to topic2.
 * 
 * If the application wants to use the native serialization and deserialization provided by Kafka rather than using 
 * the message converters provided by Spring Cloud Stream, the following properties can be set.
 * 
 *   For serialization:
 *     spring.cloud.stream.bindings.output.useNativeEncoding=true 
 * 
 *   For deserialization:
 *      spring.cloud.stream.bindings.input.useNativeDecoding=true
 * 
 */
// @EnableBinding(Processor.class)
public class UppercaseProcessor {

    // @StreamListener(Processor.INPUT)
    // @SendTo(Processor.OUTPUT)
    public String process(String message) {
        
        return message.toUpperCase();
    }
    
}
