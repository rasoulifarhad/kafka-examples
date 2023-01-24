package com.farhad.example.basickafkasample;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class Sender {

    private final KafkaTemplate<String, String> kafkaTemplate;

    // public Sender(KafkaTemplate<String, String> template) {

    //     this.kafkaTemplate = template ;
    // }

    public ListenableFuture<SendResult<String,String>> sendAsync(String topic, String key,String text ) {
        ProducerRecord<String,String> record= new ProducerRecord<String,String>(topic, key,  text);

        return  kafkaTemplate.send(record);
    }


    public ListenableFuture<SendResult<String,String>> sendAsyncWithLogCallback(String topic, String key,String text ) {
        ProducerRecord<String,String> record= new ProducerRecord<String,String>(topic, key,  text);

        ListenableFuture<SendResult<String, String>> sendFuture =  kafkaTemplate.send(record);
        sendFuture.addCallback(result -> {
            log.info("{} successfully sended. metadata: {}",result.getProducerRecord(),
                                                            result.getRecordMetadata());            
            },ex -> {
                log.info("{} ",ex);            

            });

        return sendFuture;    
    }

    public SendResult<String,String> sendBlocking(String topic,String key, String text ) {

        try {
            ListenableFuture<SendResult<String,String>> sendFuture = kafkaTemplate.send(topic,key,text);
            return sendFuture.get(10,TimeUnit.SECONDS);
            // log.info("sendBlocking reult: {}",result);
        } catch ( ExecutionException ex) {
            log.info("ExecutionException: {} ",ex);            
            throw new RuntimeException(ex.getMessage(),ex) ;
        } catch (TimeoutException | InterruptedException ex) {
            log.info("Exception: {} ",ex);            
            throw new RuntimeException(ex.getMessage(),ex) ;
        }
    }
}
