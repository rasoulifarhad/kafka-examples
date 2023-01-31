package com.farhad.example.basickafkasample.consumer.sendto;

import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.listener.ContainerGroupSequencer;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.MessageBuilder;
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
 * 
 * In order to support @SendTo, the listener container factory must be provided with a KafkaTemplate (in its replyTemplate 
 * property), which is used to send the reply. 
 * 
 * This should be a KafkaTemplate and not a ReplyingKafkaTemplate which is used on the client-side for request/reply 
 * processing. 
 * 
 * When using Spring Boot, boot will auto-configure the template into the factory; 
 *  
 * You can add a ReplyHeadersConfigurer to the listener container factory. This is consulted to determine which headers 
 * you want to set in the reply message.
 * 
 * When you use @SendTo, you must configure the ConcurrentKafkaListenerContainerFactory with a KafkaTemplate in its 
 * replyTemplate property to perform the send.
 * 
 * If the listener method returns Message<?> or Collection<Message<?>>, the listener method is responsible for setting 
 * up the message headers for the reply.
 * 
 * You can annotate a @KafkaListener method with @SendTo even if no result is returned. This is to allow the configuration 
 * of an errorHandler that can forward information about a failed message delivery to some topic.
 * 
 *   @KafkaListener(id = "voidListenerWithReplyingErrorHandler", topics= "someTopic",errorHandler = "voidSendToErrorHandler")
 *   @SendTo("failures")
 *   public void voidListenerWithReplyingErrorHandler(String in) {
 *        throw new RuntimeException("fail");
 *   }
 *   
 *   @Bean
 *   public KafkaListenerErrorHandler voidSendToErrorHandler() {
 *      return (m, e) -> {
 *          return ... // some information about the failure and input data
 *      };
 *   }
 * 
 * If a listener method returns an Iterable, by default a record for each element as the value is sent. 
 * 
 * Starting with version 2.3.5, set the splitIterables property on @KafkaListener to false and the entire result will be sent as 
 * the value of a single ProducerRecord. 
 * 
 * This requires a suitable serializer in the reply template’s producer configuration. 
 * 
 * However, if the reply is Iterable<Message<?>> the property is ignored and each message is sent separately.
 * 
 */
@Component
@Slf4j
public class Listener {
    

    @KafkaListener(id = EXAMPLE_TOPIC_GROUP_0, topics = EXAMPLE_TOPIC_0, containerGroup = "g1")
    @SendTo(SEND_TO_EXAMPLE_TOPIC_0)  // static reply topic definition
    public String upcaseReplyingListener(@Payload String in,
                                         @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {

        log.info("upcaseReplyingListener -> Received: {} From: {} Then Sended To: {}", 
                                                            in , 
                                                            topic,
                                                            SEND_TO_EXAMPLE_TOPIC_0);

        return in.toUpperCase() ;
    }


    @KafkaListener(id = EXAMPLE_TOPIC_GROUP_1, topics = EXAMPLE_TOPIC_1,containerGroup = "g2")
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


    @KafkaListener(id = EXAMPLE_TOPIC_GROUP_2, topics = EXAMPLE_TOPIC_2,containerGroup = "g3")
    @SendTo("!{request.value}") // runtime SpEL
    public String upcaseReplyingListenerPayloadValue(@Payload String in,
                                                     @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {

        log.info("UpcaseReplyingListenerPayloadValue -> Received: {} From: {} ", 
                                                                in , 
                                                                topic,
                                                                in);
        return in.toUpperCase() ;
    }

    @KafkaListener(id = EXAMPLE_TOPIC_GROUP_3, topics = EXAMPLE_TOPIC_3,containerGroup = "g4")
    // @SendTo
    public Message<String>  upcaseReplyingListenerReturnMessage(
                                                String in,
                                                @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key,
                                                @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                                                @Header(KafkaHeaders.REPLY_TOPIC) byte[] replyTo,
                                                @Header(KafkaHeaders.CORRELATION_ID) byte[] correlationId) {

        log.info("UpcaseReplyingListenerReturnMessage -> Received: {} From: {} ", 
                                                                in , 
                                                                topic,
                                                                in);
        return MessageBuilder
                        .withPayload(in.toUpperCase())
                        .setHeader(KafkaHeaders.TOPIC, replyTo)
                        .setHeader(KafkaHeaders.MESSAGE_KEY, key)
                        .setHeader(KafkaHeaders.CORRELATION_ID, correlationId)
                        .setHeader("someOtherHeader", "someValue")
                        .build();
        
    }


    /**
     * 
     * Starting @KafkaListener s in Sequence
     * 
     * A common use case is to start a listener after another listener has consumed all the records in a topic. 
     * 
     * For example, you may want to load the contents of one or more compacted topics into memory before processing 
     * records from other topics. 
     * 
     * Starting with version 2.7.3, a new component ContainerGroupSequencer has been introduced. It uses the @KafkaListener 
     * containerGroup property to group containers together and start the containers in the next group, when all the containers 
     * in the current group have gone idle.
     * 
     * 
     * During application context initialization, the sequencer, sets the autoStartup property of all the containers in the 
     * provided groups to false. It also sets the idleEventInterval for any containers (that do not already have one set) 
     * to the supplied value (5000ms in this case). Then, when the sequencer is started by the application context, the 
     * containers in the first group are started.
     * 
     * As ListenerContainerIdleEvent s are received, each individual child container in each container is stopped. When all 
     * child containers in a ConcurrentMessageListenerContainer are stopped, the parent container is stopped. When all 
     * containers in a group have been stopped, the containers in the next group are started. There is no limit to the 
     * number of groups or containers in a group.
     * 
     */
    @Bean
    public ContainerGroupSequencer containerGroupSequencer(KafkaListenerEndpointRegistry registry) {

        return new ContainerGroupSequencer(registry, 5000,"g1","g2","g3","g4");

    }

}
