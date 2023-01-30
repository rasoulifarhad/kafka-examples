package com.farhad.example.basickafkasample.consumer.seek.tolast;

import java.util.Map;

import org.apache.kafka.common.TopicPartition;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.AbstractConsumerSeekAware;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * seekRelative was added in version 2.3, to perform relative seeks.
 * 
 *   - offset negative and toCurrent false - seek relative to the end of the partition.
 * 
 *   - offset positive and toCurrent false - seek relative to the beginning of the partition.
 * 
 *   - offset negative and toCurrent true - seek relative to the current position (rewind).
 * 
 *   - offset positive and toCurrent true - seek relative to the current position (fast forward).
 * 
 * Version 2.6 added convenience methods to the abstract class:
 * 
 *   - seekToBeginning() - seeks all assigned partitions to the beginning
 * 
 *   - seekToEnd() - seeks all assigned partitions to the end
 * 
 *   - seekToTimestamp(long time) - seeks all assigned partitions to the offset represented by that timestamp.
 * 
 */

@Component
@Slf4j
public class SeekToLastOnIdleListener extends AbstractConsumerSeekAware {

    private static final String TOPIC = "seek-on-idle-topic";
    private static final String GROUP = "seek-on-idle-group";

    
    @KafkaListener(id = GROUP,topics = TOPIC)
    public void listen(String in ,
                        @Header(KafkaHeaders.RECEIVED_TOPIC) String topic ,
                        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition ,
                        @Header(KafkaHeaders.OFFSET) int offset  ) {    

        log.info("Received: {} From: (t) {} - (p) {} - (o) {}  ",
                                    in,
                                    topic,
                                    partition,
                                    offset );
    }

    @Override
    public void onIdleContainer(Map<TopicPartition, Long> assignments, ConsumerSeekCallback callback) {

        assignments
            .keySet()
            .forEach(tp -> 
                        callback.seekRelative(tp.topic(), tp.partition(), 1, false) );
    } 

    public void rewindAllOneRecord() {
        getSeekCallbacks()
                    .forEach((tp, callback) -> 
                            callback.seekRelative(tp.topic(), tp.partition(), -1, true)
                    );
    }

    public void rewindOnePartitionOneRecord(String topic , int  partition) {
        getSeekCallbackFor(new TopicPartition(topic, partition))
                    .seekRelative(topic, partition, -1 , true);
    }
    
}
