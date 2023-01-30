package com.farhad.example.basickafkasample.consumer.sendto;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

import static com.farhad.example.basickafkasample.consumer.sendto.KafkaConstants.*;

/**
 * The @SendTo value can have several forms:
 * 
 *   - @SendTo("someTopic") routes to the literal topic
 * 
 *   - @SendTo("#{someExpression}") routes to the topic determined by evaluating the expression once during application 
 *     context initialization.
 * 
 *   - @SendTo("!{someExpression}") routes to the topic determined by evaluating the expression at runtime. The #root 
 *     object for the evaluation has three properties:
 * 
 *       - request: The inbound ConsumerRecord (or ConsumerRecords object for a batch listener))
 * 
 *       - source: The org.springframework.messaging.Message<?> converted from the request.
 * 
 *       - result: The method return result.
 * 
 *   - @SendTo (no properties): This is treated as !{source.headers['kafka_replyTopic']}
 */
@Component
@Slf4j
public class Listener {
    

    @KafkaListener(id = EXAMPLE_TOPIC_GROUP_0, topics = EXAMPLE_TOPIC_0)
    @SendTo(SEND_TO_EXAMPLE_TOPIC_0)  // static reply topic definition
    public String upcaseReplyingListener(@Payload String in,
                                         @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {

        log.info("upcaseReplyingListener -> Received: {} From: {} Then Sended To: {}", 
                                                            in , 
                                                            topic,
                                                            SEND_TO_EXAMPLE_TOPIC_0);

        return in.toUpperCase() ;
    }

    /**
     * @SendTo (no properties): This is treated as !{source.headers['kafka_replyTopic']}
     * 
     */
    @KafkaListener(id = EXAMPLE_TOPIC_GROUP_1, topics = EXAMPLE_TOPIC_1)
    @SendTo()
    public String upcaseReplyingListenerEmptySendTo(@Payload String in,
                                                    @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                                                    @Header(KafkaHeaders.REPLY_TOPIC) String replyTopic) {


        log.info("UpcaseReplyingListenerEmptySendTo -> Received: {} From: {} ", 
                                                                in , 
                                                                topic,
                                                                replyTopic);
        return in.toUpperCase() ;
    }


    @KafkaListener(id = EXAMPLE_TOPIC_GROUP_2, topics = EXAMPLE_TOPIC_2)
    @SendTo("!{request.value}") // runtime SpEL
    public String upcaseReplyingListenerPayloadValue(@Payload String in,
                                                     @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {


        log.info("UpcaseReplyingListenerPayloadValue -> Received: {} From: {} ", 
                                                                in , 
                                                                topic,
                                                                in);
        return in.toUpperCase() ;
    }

}
