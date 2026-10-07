package com.example.hr.application.business.event;

import java.time.ZonedDateTime;
import java.util.UUID;

public sealed abstract class HrEvent<Identity> permits EmployeeFiredEvent, EmployeeHiredEvent,EmployeeSalaryUpdatedEvent {
	private final String id = UUID.randomUUID().toString();
	private final ZonedDateTime dateTime = ZonedDateTime.now();
	private final HrEventType eventType;
	private final Identity identity;

	public HrEvent(HrEventType eventType, Identity identity) {
		this.eventType = eventType;
		this.identity = identity;
	}

	public String getId() {
		return id;
	}

	public ZonedDateTime getDateTime() {
		return dateTime;
	}

	public HrEventType getEventType() {
		return eventType;
	}

	public Identity getIdentity() {
		return identity;
	}

	@Override
	public String toString() {
		return "HrEvent [id=" + id + ", dateTime=" + dateTime + ", eventType=" + eventType + ", identity=" + identity
				+ "]";
	}

}
