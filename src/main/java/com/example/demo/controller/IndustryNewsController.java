package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Topic;

@RestController
@RequestMapping("kafka")
public class IndustryNewsController {
	@Autowired
	private KafkaTemplate<String, Topic> kafkaTemplate;
	private static final String TOPIC = "myTopicJson";

	@GetMapping("/publish/{message}")
	public String post(@PathVariable final String message) {
		kafkaTemplate.send(TOPIC, new Topic(message, "kafka course", "kafka description"));
		return "published succesfully";
	}

}
