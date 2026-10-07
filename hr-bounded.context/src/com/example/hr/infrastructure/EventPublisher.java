package com.example.hr.infrastructure;

import com.example.hexagonal.helper.Port;
import com.example.hexagonal.helper.PortType;

@Port(PortType.DRIVEN)
public interface EventPublisher<Event> {
	void publishEvent(Event event);
}
