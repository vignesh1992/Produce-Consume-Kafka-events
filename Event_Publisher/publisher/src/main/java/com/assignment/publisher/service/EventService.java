package com.assignment.publisher.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.assignment.publisher.model.EventRequest;
import com.protobuf.model.EventMessage;
import com.protobuf.model.StreamMessage;

@Service
public class EventService {

	private static final Logger logger = LoggerFactory.getLogger(EventService.class);

	@Autowired
	private KafkaTemplate<String, StreamMessage> kafkaTemplate;

	private static final String TOPIC = "events";

	@Async
	public void publishEvent(EventRequest eventRequest) {
		logger.info("Attempting to publish an event to kafka topic");

		StreamMessage streamMessage = StreamMessage.newBuilder()
				.setMessage(EventMessage.newBuilder().setTimestamp(eventRequest.getTimestamp())
						.setEvent(eventRequest.getEvent()).setUserId(eventRequest.getUserId()).build())
				.build();

		kafkaTemplate.send(TOPIC, streamMessage)
				.addCallback(new ListenableFutureCallback<SendResult<String, StreamMessage>>() {
					@Override
					public void onFailure(Throwable throwable) {
						logger.error("Unable to send message " + streamMessage + " resulted in failure message: "
								+ throwable.getMessage());
					}

					@Override
					public void onSuccess(SendResult<String, StreamMessage> result) {
						logger.info("Successfully sent an event to Kafka topic: " + streamMessage + " :with offset "
								+ result.getRecordMetadata().offset());
					}
				});
	}

}
