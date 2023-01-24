package com.farhad.example.basickafkasample;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.kafka.core.KafkaSendCallback;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.farhad.example.basickafkasample.Configuration.*;

@Configuration
@Slf4j
@RequiredArgsConstructor
@DependsOn
public class BootRunner {
    
     
    private final Sender sender;

    @Bean
    @Order(10)
    public ApplicationRunner runnerBlocking() {

        return args ->  {
             sender.sendBlocking(TOPIC, "runnerBlocking_key1", "value1");
        };
    }

    @Bean
    @Order(20)
    public ApplicationRunner runnerAsync1() {

        return args ->  {

            
            ListenableFuture<SendResult<String,String>> sendFuture = sender.sendAsync(TOPIC, "runnerAsync1_key1", "value1");

            sendFuture.addCallback(new ListenableFutureCallback<SendResult<String,String>>() {

                @Override
                public void onSuccess(SendResult<String,String> result) {
    
                    log.info("{} successfully sended. metadata: {}",result.getProducerRecord(),
                                                             result.getRecordMetadata());
                }
    
                @Override
                public void onFailure(Throwable ex) {
                    log.info("{} ",ex);            
                    
                }
            });
    
        };
    }

    @Bean
    @Order(30)
    public ApplicationRunner runnerAsync2() {

        return args ->  {

            ListenableFuture<SendResult<String,String>> sendFuture = sender.sendAsync(TOPIC, "runnerAsync2_key1", "value1");

            sendFuture.addCallback(new KafkaSendCallback<String,String>() {

                @Override
                public void onSuccess(SendResult<String, String> result) {
                    log.info("{} successfully sended. metadata: {}",result.getProducerRecord(),
                                                            result.getRecordMetadata());
                    
                }

                @Override
                public void onFailure(Throwable ex) {
                    log.info("{} ",ex);            
                    
                }

                @Override
                public void onFailure(KafkaProducerException ex) {
                    log.info("faile record: {}",ex.getFailedProducerRecord());            
            
                }
                
            });
        };
    }

    @Bean
    @Order(40)
    public ApplicationRunner runnerAsync3() {

        return args ->  {

            sender.sendAsyncWithLogCallback(TOPIC, "runnerAsync3_key1", "value1");
        };
    }

}
