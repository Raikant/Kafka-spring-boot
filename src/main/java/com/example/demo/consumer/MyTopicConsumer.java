package com.example.demo.consumer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.example.demo.entity.Person;
import com.example.demo.entity.Topic;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MyTopicConsumer {
	private final List<Object> messages = new ArrayList<>();

	@KafkaListener(topics = "topic", groupId = "kafka-sandbox")
	public void topics(List<Topic> topics) {
		log.info("topic: {}", topics);
	}

	@KafkaListener(topics = "person", groupId = "kafka-sandbox-json")
	public void persons(List<Person> persons) {
		log.info("person: {}", persons);
	}

	public List<Object> getMessages() {
		return messages;
	}
}
