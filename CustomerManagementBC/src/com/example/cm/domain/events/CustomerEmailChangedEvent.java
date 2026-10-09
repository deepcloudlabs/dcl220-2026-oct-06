package com.example.cm.domain.events;

import com.example.cm.domain.CustomerID;
import com.example.cm.domain.Email;

// Immutable 
public final class CustomerEmailChangedEvent extends CustomerManagementEvent {
	private final Email previousEmail;
	private final Email nextEmail;

	public CustomerEmailChangedEvent(CustomerID customerId, Email previousEmail, Email nextEmail) {
		super(customerId);
		this.previousEmail = previousEmail;
		this.nextEmail = nextEmail;
	}

	public Email getPreviousEmail() {
		return previousEmail;
	}

	public Email getNextEmail() {
		return nextEmail;
	}

}
