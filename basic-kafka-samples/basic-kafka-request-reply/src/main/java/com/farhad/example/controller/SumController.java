package com.farhad.example.controller;

import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.farhad.example.model.TwoNumber;

@RestController
public class SumController {
	
	@Autowired
	ReplyingKafkaTemplate<String, TwoNumber,TwoNumber> kafkaTemplate;
	
	@Value("${kafka.topic.request-topic}")
	String requestTopic;
	
	@Value("${kafka.topic.requestreply-topic}")
	String requestReplyTopic;
	
	@ResponseBody
	@PostMapping(value="/api/sum",produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public TwoNumber sum(@RequestBody TwoNumber request) throws InterruptedException, ExecutionException {

		ProducerRecord<String, TwoNumber> record = new ProducerRecord<String, TwoNumber>(requestTopic, request);

		record.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, requestReplyTopic.getBytes()));
		
		RequestReplyFuture<String, TwoNumber, TwoNumber> sendAndReceive = kafkaTemplate.sendAndReceive(record);

		// producer produced successfully ??
		SendResult<String, TwoNumber> sendResult = sendAndReceive.getSendFuture().get();
		
		//print all headers
		sendResult.getProducerRecord().headers().forEach(header -> System.out.println(header.key() + ":" + header.value().toString()));
		
		// get consumer record
		ConsumerRecord<String, TwoNumber> consumerRecord = sendAndReceive.get();

		return consumerRecord.value();		
	}

}
