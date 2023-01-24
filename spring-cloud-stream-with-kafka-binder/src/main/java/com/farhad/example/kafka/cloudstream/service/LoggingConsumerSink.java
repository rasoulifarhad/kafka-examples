package com.farhad.example.kafka.cloudstream.service;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Configuration;

import com.farhad.example.kafka.cloudstream.model.Person;

import lombok.extern.slf4j.Slf4j;

/**
 * Note that @EnableBinding is provided with a Sink, which indicates that this is a consumer. 
 * 
 * How is the message coming from the Kafka topic converted to this POJO? 
 * 
 *   - Spring Cloud Stream provides automatic content-type conversions. 
 *   - By default, it uses application/JSON as the content type, but other content types 
 *     are supported as well. 
 *   - You can provide the content type by using the property 
 *          spring.cloud.stream.bindings.input.contentType, 
 *     and then set it to the appropriate content types, such as application/Avro.
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
// @EnableBinding(Sink.class)
@Slf4j
public class LoggingConsumerSink {


    // @StreamListener(Sink.INPUT)
    public void handle(Person person) {
        log.info("Received: {}", person);
    }
    
}
