package com.example.cm.application.business;

import java.util.Objects;
import java.util.Optional;

import com.example.cm.application.CustomerManagementApplication;
import com.example.cm.domain.Customer;
import com.example.cm.domain.CustomerID;
import com.example.cm.domain.Email;
import com.example.cm.domain.Phone;
import com.example.cm.domain.events.CustomerEmailChangedEvent;
import com.example.cm.domain.events.CustomerManagementEvent;
import com.example.cm.domain.events.CustomerPrimaryPhoneChangedEvent;
import com.example.cm.domain.events.CustomerRegisteredEvent;
import com.example.cm.infrastructure.EventPublisher;
import com.example.cm.repository.CustomerRepository;
import com.example.hexagonal.helper.Application;

@Application(port = CustomerManagementApplication.class)
public class StandardCustomerManagementApplication implements CustomerManagementApplication {

	private final CustomerRepository customerRepository;
	private final EventPublisher<CustomerManagementEvent> eventPublisher;
	
	public StandardCustomerManagementApplication(CustomerRepository customerRepository, EventPublisher<CustomerManagementEvent> eventPublisher) {
		this.customerRepository = customerRepository;
		this.eventPublisher = eventPublisher;
	}

	@Override
	public void registerCustomer(Customer customer) {
		Objects.requireNonNull(customer, "Customer cannot be null");
		customerRepository.persist(customer);
		var event = new CustomerRegisteredEvent(customer);
		eventPublisher.publish(event);
	}

	@Override
	public Optional<Customer> findCustomerById(CustomerID customerId) {
		Objects.requireNonNull(customerId, "CustomerID cannot be null");
		return customerRepository.findCustomerById(customerId);
	}

	@Override
	public Optional<Customer> changeEmail(CustomerID customerId, Email email) {
		Objects.requireNonNull(customerId, "CustomerID cannot be null");
		Objects.requireNonNull(email, "Email cannot be null");
		var customer = customerRepository.findCustomerById(customerId)
				.orElseThrow(() -> new IllegalArgumentException("%s does not exist".formatted(customerId)));
		var oldEmail = customer.getCustomerContact().getEmail();
		customer.changeEmail(email);
		customerRepository.updateCustomer(customer);
		var event = new CustomerEmailChangedEvent(customerId, oldEmail, email);
		eventPublisher.publish(event);
		return Optional.of(customer);
	}

	@Override
	public Optional<Customer> changePrimaryPhone(CustomerID customerId, Phone phone) {
		Objects.requireNonNull(customerId, "CustomerID cannot be null");
		Objects.requireNonNull(phone, "Phone cannot be null");
		var customer = customerRepository.findCustomerById(customerId)
				.orElseThrow(() -> new IllegalArgumentException("%s does not exist".formatted(customerId)));
		Phone oldPrimaryPhone = customer.getPrimaryPhone();
		customer.makePrimaryPhone(phone);
		Optional<Customer> updatedCustomer = Optional.of(customerRepository.updateCustomer(customer));
		var event = new CustomerPrimaryPhoneChangedEvent(customerId, oldPrimaryPhone , phone);
		eventPublisher.publish(event);		
		return updatedCustomer;
	}

}
