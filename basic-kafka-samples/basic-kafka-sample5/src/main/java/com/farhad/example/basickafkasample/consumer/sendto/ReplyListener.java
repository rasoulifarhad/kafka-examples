package com.farhad.example.basickafkasample.consumer.sendto;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

import static com.farhad.example.basickafkasample.consumer.sendto.KafkaConstants.*;


@Component
@Slf4j
public class ReplyListener {
    

    @KafkaListener(id = SEND_TO_EXAMPLE_GROUP_0, topics = SEND_TO_EXAMPLE_TOPIC_0)
    public void upcaseReplyListener(@Payload String in,
                                    @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {

        log.info("UpcaseReplyListener -> Reply Received: {} From: {} ", in , topic);
    }

    @KafkaListener(id = SEND_TO_EXAMPLE_GROUP_1, topics = SEND_TO_EXAMPLE_TOPIC_1)
    public void upcaseReplyListenerEmptySendTo(@Payload String in,
                                               @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {

        log.info("UpcaseReplyListenerEmptySendTo -> Reply Received: {} From: {} ", in , topic);
    }

    @KafkaListener(id = SEND_TO_EXAMPLE_GROUP_2, topics = SEND_TO_EXAMPLE_TOPIC_2)
    public void upcaseReplyListenerPayloadValue(@Payload String in,
                                                @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {

        log.info("UpcaseReplyListenerPayloadValue -> Reply Received: {} From: {} ", in , topic);
    }

}
