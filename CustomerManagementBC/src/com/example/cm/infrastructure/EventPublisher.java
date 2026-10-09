package com.example.cm.infrastructure;

import com.example.hexagonal.helper.Port;
import com.example.hexagonal.helper.PortType;

@Port(PortType.DRIVEN_PORT)
public interface EventPublisher<E> {
	void publish(E event);
}
