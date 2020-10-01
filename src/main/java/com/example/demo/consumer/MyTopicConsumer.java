package com.example.demo.consumer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
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

	@KafkaListener(topics = "streams-pipe-output", groupId = "kafka-sandbox-json")
	public void streamOutput(String textLines) {
		log.info("textLines: {}", textLines);
	}

	@KafkaListener(topics = "streams-plaintext-input", groupId = "kafka-sandbox-json")
	public void streamInput(String sentence) {
		Properties streamsConfiguration = streamconfigurations();
		StreamsBuilder builder = new StreamsBuilder();
		KStream<String, String> textLines = builder.stream("streams-plaintext-input");
		Pattern pattern = Pattern.compile("\\W+", Pattern.UNICODE_CHARACTER_CLASS);

		KTable<String, Long> wordCounts = textLines
				.flatMapValues(value -> Arrays.asList(pattern.split(value.toLowerCase()))).groupBy((key, word) -> word)
				.count();
		textLines.to("streams-pipe-output");
		final Topology topology = builder.build();
		final KafkaStreams streams = new KafkaStreams(topology, streamsConfiguration);
		streams.start();
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		streams.close();
	}

	private Properties streamconfigurations() {
		String bootstrapServers = "localhost:9092";
		Properties streamsConfiguration = new Properties();
		streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, "stream-pipe");
		streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		streamsConfiguration.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
		streamsConfiguration.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
		// Optional
		streamsConfiguration.setProperty(StreamsConfig.TOPOLOGY_OPTIMIZATION, StreamsConfig.OPTIMIZE);

		return streamsConfiguration;
	}

	public List<Object> getMessages() {
		return messages;
	}
}
