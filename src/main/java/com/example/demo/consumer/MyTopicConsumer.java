package com.example.demo.consumer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.example.demo.entity.Topic;

@Component
public class MyTopicConsumer {
	private final List<String> messages = new ArrayList<>();

	@KafkaListener(topics = "myTopic", groupId = "kafka-sandbox")
	public void listen(String message) {
		System.out.println(message);
		synchronized (messages) {
			messages.add(message);
		}
	}

	@KafkaListener(topics = "myTopicJson", groupId = "kafka-sandbox-json")
	public void consumeJson(Topic topic) {
		System.out.println("Consumed a json message: " + topic);
	}

	public List<String> getMessages() {
		return messages;
	}
}
