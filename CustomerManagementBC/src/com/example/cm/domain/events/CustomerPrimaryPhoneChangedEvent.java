package com.example.cm.domain.events;

import com.example.cm.domain.CustomerID;
import com.example.cm.domain.Phone;

public final class CustomerPrimaryPhoneChangedEvent extends CustomerManagementEvent {

	private final Phone previousPrimaryPhone;
	private final Phone nextPrimaryPhone;

	public CustomerPrimaryPhoneChangedEvent(CustomerID customerId,Phone previousPrimaryPhone,Phone nextPrimaryPhone) {
		super(customerId);
		this.previousPrimaryPhone = previousPrimaryPhone;
		this.nextPrimaryPhone = nextPrimaryPhone;
	}

	public Phone getPreviousPrimaryPhone() {
		return previousPrimaryPhone;
	}

	public Phone getNextPrimaryPhone() {
		return nextPrimaryPhone;
	}

}
