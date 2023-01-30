package com.farhad.example.basickafkasample.consumer.seek;

import java.util.Scanner;
import java.util.stream.IntStream;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

import lombok.extern.slf4j.Slf4j;

import static com.farhad.example.basickafkasample.consumer.seek.KafkaConstants.*;


@Configuration
@Slf4j
public class SeekExampleBoot {
    

    @Bean
    public ApplicationRunner runner(Listener listener ,
                                    KafkaTemplate<String,String> kafkaTemplate ) {

        return args -> {

            log.info("Start sending ....");
            IntStream.range(0, 10)
                      .forEach(value -> 
                                kafkaTemplate.send(new ProducerRecord<String,String>(SEEK_EXAMPLE_TOPIC, 
                                                                                     value % 3,  
                                                                                     "foo" + value , 
                  
                                                                                     "bar" + value)));

            Scanner scanner = new Scanner(System.in);
            while(true) {
                    int choose = scanner.nextInt();
                    switch (choose) {
                        case 0:
                            System.out.println("choose one:");
                            System.out.println("---- (1) for seek to start ");
                            System.out.println("---- (2) for seek to end ");
                            System.out.println("---- (any posetive) for seek to offset ");
                            System.out.println("---- (any negative) quit ");
                            break;
                        case 1:
                            listener.seekToStart();
                            break;
                    
                        case 2:
                            listener.seekToEnd();
                            break;
                        
                        default:
                            if (choose  > 0 ) {
                                listener.seekToOffset(choose);           
                            } else {
                                return;
                            }
                            break;
                    
                }
            }
            
                    
        };

    }
}
