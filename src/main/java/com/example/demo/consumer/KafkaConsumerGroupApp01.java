package com.example.demo.consumer;

import java.util.ArrayList;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.IndustryNews;
import com.example.demo.repository.IndustryNewsRepository;
import com.google.gson.Gson;

@Service
public class KafkaConsumerGroupApp01 {

	@Autowired
	private IndustryNewsRepository newsRepository;

	public void callAndSave() {
		Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9092,localhost:9093");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("group.id", "test");

		KafkaConsumer<String, String> myConsumer = new KafkaConsumer<String, String>(props);
		Gson gson = new Gson();
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
						newsRepository.saveAll(newsList);
						newsList.clear();
					}
				}
				if (newsList.size() > 0) {
					newsList.forEach(news -> {
						System.out.println(news);
					});
					try {
						System.out.println(newsRepository == null);
						newsRepository.saveAll(newsList);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			myConsumer.close();
		}
	}

	public static void main(String[] args) {
		new KafkaConsumerGroupApp01().callAndSave();
	}

}
