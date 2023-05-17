package com.farhad.example.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import com.farhad.example.model.TwoNumber;


@Component
public class ReplyingKafkaConsumer {
	 
	 @KafkaListener(id= "my-group",topics = "${kafka.topic.request-topic}" )
	 @SendTo
	  public TwoNumber listen(TwoNumber towNumber) throws InterruptedException {
		 
		 int sum = towNumber.getFirstNumber() + towNumber.getSecondNumber();
		 towNumber.setAdditionalProperty("sum", sum);
		 return towNumber;
	  }

}
