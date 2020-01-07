package com.example.demo.consumer;

import java.util.ArrayList;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

public class KafkaConsumerSubscribeApp {
	public static void main(String[] args) {
		Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9092");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("group.id", "test");

		KafkaConsumer<String, String> myConsumer = new KafkaConsumer<String, String>(props);

		ArrayList<String> topics = new ArrayList<String>();
		ArrayList<TopicPartition> partitions = new ArrayList<>();

		topics.add("myTopic");
		TopicPartition myTopicPart0 = new TopicPartition("myTopic", 0);
		partitions.add(myTopicPart0);

		myConsumer.subscribe(topics);

		try {
			while (true) {
				ConsumerRecords<String, String> records = myConsumer.poll(10);
//				myConsumer.seek(myTopicPart0, myConsumer.endOffsets(partitions).get(myTopicPart0));
//				System.out.println("offset: " + myConsumer.endOffsets(partitions));
				for (ConsumerRecord<String, String> record : records) {
					System.out.println((String.format("Topics: %s,Partition: %d,Offset: %d,Key: %s,value: %s",
							record.topic(), record.partition(), record.offset(), record.key(), record.value())));
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			myConsumer.close();
		}
	}
}
