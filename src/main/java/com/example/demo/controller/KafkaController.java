package com.example.demo.controller;

import java.util.List;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.consumer.MyTopicConsumer;

@RestController
public class KafkaController {
	private KafkaTemplate<String, String> kafkaTemplate;
	private MyTopicConsumer myTopicConsumer;

	public KafkaController(KafkaTemplate<String, String> template, MyTopicConsumer myTopicConsumer) {
		this.kafkaTemplate = template;
		this.myTopicConsumer = myTopicConsumer;
	}

	@GetMapping("/kafka/produce")
	public void produce(@RequestParam String message) {
		kafkaTemplate.send("myTopic", message);
	}

	@GetMapping("/kafka/messages")
	public List<String> getMessages() {
		return myTopicConsumer.getMessages();
	}

}
