package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.demo.consumer.MyTopicConsumer;
import com.example.demo.entity.TopicVo;
import com.google.gson.Gson;

@RestController
public class KafkaController {
	private KafkaTemplate<String, String> kafkaTemplate;
	private MyTopicConsumer myTopicConsumer;
	@Autowired
	private RestTemplate restTemplate;

	public KafkaController(KafkaTemplate<String, String> template, MyTopicConsumer myTopicConsumer) {
		this.kafkaTemplate = template;
		this.myTopicConsumer = myTopicConsumer;
	}

	@GetMapping("/kafka/produce")
	public void produce(@RequestParam String message) {
		ResponseEntity<TopicVo> topicVo = restTemplate.getForEntity("http://localhost:8081/topics", TopicVo.class);
		if (topicVo != null)
			topicVo.getBody().getTopics().forEach(topic -> {
				kafkaTemplate.send("myTopic", new Gson().toJson(topic));
			});
		else
			System.out.println("topic is null");
	}

	@GetMapping("/kafka/messages")
	public List<String> getMessages() {
		return myTopicConsumer.getMessages();
	}

}
