package com.farhad.demo.kafka.springbootwithkafka.rest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.farhad.demo.kafka.springbootwithkafka.service.Producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = "/api/kafka")
@RequiredArgsConstructor
@Slf4j  
public class MessageResource {
    
    private final Producer producer ;   

    /**
     * Test with curl
     * 
     *   curl -X POST -F 'message=test' http://localhost:9080/api/kafka/publish
     */
    @PostMapping("/publish")
    public void sendMessageToKafka(@RequestParam("message") String message) {
        log.info("");
        producer.sendMessage(message);
    }

    /**
     * Test with curl
     * 
     *   curl -X POST -F 'message=test' http://localhost:9080/api/kafka/publish/topic1
     */
    @PostMapping("/publish/{whichTopic}")
    public void sendMessageToKafka(@RequestParam("message") String message,@PathVariable("whichTopic") String topic) {
        log.info("");
        producer.sendMessage(message,topic);
    }

}
