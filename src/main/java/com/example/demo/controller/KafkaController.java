package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.demo.consumer.MyTopicConsumer;
import com.example.demo.entity.IndustryNews;
import com.example.demo.entity.IndustryNewsVo;
import com.example.demo.entity.Topic;
import com.example.demo.repository.IndustryNewsRepository;

@RestController
public class KafkaController {
	private KafkaTemplate<String, Topic> kafkaTemplate;
	private MyTopicConsumer myTopicConsumer;
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private IndustryNewsRepository newsRepository;

	private String industry_news_uri = "http://api.saverisk.com/rest/v1/company?key=0de23766-f58a-4168-814c-bd578d92e635&d=PF_Industry_News&q=";

	public KafkaController(KafkaTemplate<String, Topic> template, MyTopicConsumer myTopicConsumer) {
		this.kafkaTemplate = template;
		this.myTopicConsumer = myTopicConsumer;
	}

	@GetMapping("/kafka/produce")
	public void produce(@RequestParam String message) {
		kafkaTemplate.send("myTopic",
				new Topic(String.valueOf(UUID.randomUUID()), message, "kafka messaging"));
	}

	@GetMapping("/kafka/save")
	public void save(@RequestParam String message) {
		newsRepository.deleteAllEntities();
		ResponseEntity<IndustryNewsVo> industryNewsVO = restTemplate.getForEntity(industry_news_uri + message,
				IndustryNewsVo.class);
		if (industryNewsVO != null) {
			int count = 0;
			ArrayList<IndustryNews> newsList = new ArrayList<>();
			for (IndustryNews news : industryNewsVO.getBody().getIndustryNewsList()) {
				System.out.println(news);
				newsList.add(news);
				count++;
				if (newsList.size() % 1000 == 0) {
					newsRepository.saveAll(newsList);
					newsList.clear();
				}
			}
			System.out.println("Total record saved " + count);
			newsRepository.saveAll(newsList);
		} else
			System.out.println("topic is null");
	}

	@GetMapping("/kafka/messages")
	public List<String> getMessages() {
		return myTopicConsumer.getMessages();
	}

}
