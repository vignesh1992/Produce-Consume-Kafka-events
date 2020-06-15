package com.assignment.publisher.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfiguration {

	@Configuration
	public class KafkaTopicConfiguration {

		private static final String TOPIC = "events";

		@Bean
		public NewTopic topicExample() {
			return TopicBuilder.name(TOPIC).partitions(6).build();
		}
	}
}
