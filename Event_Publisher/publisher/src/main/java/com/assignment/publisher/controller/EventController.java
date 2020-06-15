package com.assignment.publisher.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.publisher.model.EventRequest;
import com.assignment.publisher.service.EventService;

@RestController
@RequestMapping("/api")
public class EventController {

	private static final Logger logger = LoggerFactory.getLogger(EventController.class);

	private final EventService eventService;

	@Autowired
	public EventController(EventService eventService) {
		this.eventService = eventService;
	}

	@PostMapping("/events")
	public ResponseEntity<String> postEvent(@RequestBody EventRequest event) {

		logger.info("Attempting to post an event to async service");
		eventService.publishEvent(event);
		return ResponseEntity.accepted().build();
	}
}
