package com.example.demo.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.example.demo.entity.Topic;

/**
 * 
 * @author ravipathak
 * Removed configuration annotation
 *Disabling this configuration for now as consumer and producer properties are being handles
 *from application.properties file
 */
public class ProducerConfiguration {
	private static final String KAFKA_BROKER = "localhost:9092";

	@Bean
	public ProducerFactory<String, Topic> producerFactory() {
		return new DefaultKafkaProducerFactory<>(producerConfigurations());
	}

	@Bean
	public Map<String, Object> producerConfigurations() {
		Map<String, Object> configurations = new HashMap<>();
		configurations.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_BROKER);
		configurations.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configurations.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		return configurations;
	}

	@Bean
	public KafkaTemplate<String, Topic> kafkaTemplate() {
		return new KafkaTemplate<>(producerFactory());
	}
}
