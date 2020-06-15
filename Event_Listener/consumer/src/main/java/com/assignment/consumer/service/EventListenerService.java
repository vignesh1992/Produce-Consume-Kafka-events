package com.assignment.consumer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.protobuf.model.EventMessage;
import com.protobuf.model.StreamMessage;

@Service
public class EventListenerService {

	private static final Logger logger = LoggerFactory.getLogger(EventListenerService.class);

	@KafkaListener(topics = "events")
	public void listen(StreamMessage streamMessage) {

		if (null == streamMessage) {
			return;
		}

		EventMessage message = streamMessage.getMessage();

		logger.info("timestamp= " + message.getTimestamp() + " userId= " + message.getUserId() + " event= "
				+ message.getEvent());
	}
}
