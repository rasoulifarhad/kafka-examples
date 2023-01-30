package com.farhad.example.basickafkasample.consumer.seek;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.ConsumerSeekAware;
import org.springframework.stereotype.Component;

import static com.farhad.example.basickafkasample.consumer.seek.KafkaConstants.*;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class Listener implements ConsumerSeekAware {

    private final ThreadLocal<ConsumerSeekCallback> callbackForThread = new ThreadLocal<>() ;

    private final Map<TopicPartition,ConsumerSeekCallback> callbacks = new ConcurrentHashMap<>();
     

    @KafkaListener(id = "seek-example",topics = SEEK_EXAMPLE_TOPIC,concurrency = SEEK_EXAMPLE_LISTENER_CONCURRENCY)
    public void listen(ConsumerRecord<String,String>  consumerRecord) {

        log.info("Listener - Received:{} from: (t) {} - (p) {} - (o) {} - ConsumerRecord: {}" , 
                                                            consumerRecord.value() ,
                                                            consumerRecord.topic(),
                                                            consumerRecord.partition(),
                                                            consumerRecord.offset(),
                                                            consumerRecord);

    }

    @Override
    public void onPartitionsAssigned(Map<TopicPartition, Long> assignments, ConsumerSeekCallback callback) {

        assignments
            .keySet()
            .forEach(tp -> this.callbacks.put(tp, this.callbackForThread.get()) );
    }

    @Override
    public void onPartitionsRevoked(Collection<TopicPartition> partitions) {

        partitions
            .forEach(tp -> this.callbacks.remove(tp) );
    
        this.callbackForThread.remove();
    }

    @Override
    public void registerSeekCallback(ConsumerSeekCallback callback) {

        this.callbackForThread.set(callback);
    }

    @Override
    public void unregisterSeekCallback() {

        this.callbackForThread.remove();
    }

    @Override
    public void onFirstPoll() {
    }

    @Override
    public void onIdleContainer(Map<TopicPartition, Long> assignments, ConsumerSeekCallback callback) {
    }

    public void seekToStart()  {

        log.info("========= Seek To Start Begin .... =============");
        this.callbacks
                .forEach((tp, callback) ->  callback.seekToBeginning(tp.topic(), tp.partition()));
    }

    public void seekToEnd()  {

        log.info("========= Seek To End Begin .... =============");
        this.callbacks
                .forEach((tp, callback) ->  callback.seekToEnd(tp.topic(), tp.partition()));
    }

    public void seekToOffset(int offset)  {

        log.info("========= Seek To Offset {} Begin .... =============",offset);
        this.callbacks
                .forEach((tp, callback) ->  callback.seek(tp.topic(), tp.partition(),offset));
    }

}
