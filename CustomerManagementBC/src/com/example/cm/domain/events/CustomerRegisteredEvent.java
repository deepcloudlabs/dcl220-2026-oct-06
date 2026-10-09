package com.example.cm.domain.events;

import com.example.cm.domain.Customer;

public class CustomerRegisteredEvent extends CustomerManagementEvent {

	public CustomerRegisteredEvent(Customer customer) {
		super(customer.getIdentity());
	}

}
