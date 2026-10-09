package com.example.cm.adapter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.cm.domain.events.CustomerManagementEvent;
import com.example.cm.infrastructure.EventPublisher;
import com.example.hexagonal.helper.Adapter;

import tools.jackson.databind.ObjectMapper;

@Adapter(port = EventPublisher.class)
@Service
public class EventPublisherKafkaPublisherAdapter implements EventPublisher<CustomerManagementEvent> {
	private final KafkaTemplate<String, String> kafkaTemplate;
	private final String eventTopic;
	private final ObjectMapper objectMapper;
	
	public EventPublisherKafkaPublisherAdapter(
			KafkaTemplate<String, String> kafkaTemplate, 
			@Value("${eventTopic}") String eventTopic, ObjectMapper objectMapper) {
		this.kafkaTemplate = kafkaTemplate;
		this.eventTopic = eventTopic;
		this.objectMapper = objectMapper;
	}

	@Override
	public void publish(CustomerManagementEvent event) {
		var eventAsJson = objectMapper.writeValueAsString(event);
		kafkaTemplate.send(eventTopic, eventAsJson);
	}

}
