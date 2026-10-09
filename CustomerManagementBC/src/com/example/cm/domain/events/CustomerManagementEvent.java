package com.example.cm.domain.events;

import java.time.ZonedDateTime;
import java.util.UUID;

import com.example.cm.domain.CustomerID;
import com.example.ddd.helper.DomainEvent;

@DomainEvent(boundedContext = "customer management")
public abstract class CustomerManagementEvent {
	protected final String eventId = UUID.randomUUID().toString();
	protected final CustomerID customerId;
	protected final ZonedDateTime createdAt = ZonedDateTime.now();

	public CustomerManagementEvent(CustomerID customerId) {
		this.customerId = customerId;
	}

	public String getEventId() {
		return eventId;
	}

	public CustomerID getCustomerId() {
		return customerId;
	}

	public ZonedDateTime getCreatedAt() {
		return createdAt;
	}

	@Override
	public String toString() {
		return "CustomerManagementEvent [eventId=" + eventId + ", customerId=" + customerId + ", createdAt=" + createdAt
				+ "]";
	}

}
