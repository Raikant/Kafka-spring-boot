package com.example.demo.consumer;

import java.util.ArrayList;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.entity.IndustryNews;
import com.example.demo.repository.IndustryNewsRepository;
import com.google.gson.Gson;

public class KafkaConsumerGroupApp03 {
	@Autowired
	private IndustryNewsRepository newsRepository;

	public static void main(String[] args) {

		Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9092,localhost:9093");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("group.id", "test");

		KafkaConsumer<String, String> myConsumer = new KafkaConsumer<String, String>(props);
		Gson gson = new Gson();
		KafkaConsumerGroupApp03 app03 = new KafkaConsumerGroupApp03();
		ArrayList<IndustryNews> newsList = new ArrayList<>();

		ArrayList<String> topics = new ArrayList<String>();
		topics.add("myTopic");

		myConsumer.subscribe(topics);

		try {
			while (true) {
				ConsumerRecords<String, String> records = myConsumer.poll(10);
				for (ConsumerRecord<String, String> record : records) {
					IndustryNews industryNews = gson.fromJson(record.value(), IndustryNews.class);
					newsList.add(industryNews);
					if (newsList.size() % 1000 == 0) {
						app03.saveNews(newsList);
						newsList.clear();
					}
				}
				if (newsList.size() > 0)
					app03.saveNews(newsList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			myConsumer.close();
		}
	}

	private void saveNews(ArrayList<IndustryNews> newsList) {
		newsRepository.saveAll(newsList);
	}
}
