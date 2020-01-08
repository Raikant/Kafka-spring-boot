package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.demo.consumer.MyTopicConsumer;
import com.example.demo.entity.IndustryNewsVo;
import com.google.gson.Gson;

@RestController
public class KafkaController {
	private KafkaTemplate<String, String> kafkaTemplate;
	private MyTopicConsumer myTopicConsumer;
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private WebClient.Builder webClientBuilder;

	private String industry_news_uri = "http://api.saverisk.com/rest/v1/company?key=0de23766-f58a-4168-814c-bd578d92e635&d=PF_Industry_News&q=";

	public KafkaController(KafkaTemplate<String, String> template, MyTopicConsumer myTopicConsumer) {
		this.kafkaTemplate = template;
		this.myTopicConsumer = myTopicConsumer;
	}

	@GetMapping("/kafka/produce")
	public void produce(@RequestParam String message) {
//		ResponseEntity<IndustryNewsVo> industryNewsVO = restTemplate.getForEntity(industry_news_uri + message,
//				IndustryNewsVo.class);
		try {
			IndustryNewsVo mono = webClientBuilder.build().get().uri(industry_news_uri + message).retrieve()
					.bodyToMono(IndustryNewsVo.class).block();
			System.out.println("Flux created");
			mono.getIndustryNewsList().forEach(news -> {
				kafkaTemplate.send("myTopic", new Gson().toJson(news));
			});
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

//		if (industryNewsVO != null)
//			industryNewsVO.getBody().getIndustryNewsList().forEach(news -> {
//				kafkaTemplate.send("myTopic", new Gson().toJson(news));
//			});
//		else
//			System.out.println("topic is null");
	}

	@GetMapping("/kafka/messages")
	public List<String> getMessages() {
		return myTopicConsumer.getMessages();
	}

}
