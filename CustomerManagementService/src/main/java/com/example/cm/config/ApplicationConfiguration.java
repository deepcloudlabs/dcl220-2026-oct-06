package com.example.cm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.cm.application.CustomerManagementApplication;
import com.example.cm.application.business.StandardCustomerManagementApplication;
import com.example.cm.domain.events.CustomerManagementEvent;
import com.example.cm.infrastructure.EventPublisher;
import com.example.cm.repository.CustomerRepository;

@Configuration
public class ApplicationConfiguration {

	@Bean
	CustomerManagementApplication createCustomerManagementApplication(CustomerRepository customerRepository, EventPublisher<CustomerManagementEvent> eventPublisher) {
		return new StandardCustomerManagementApplication(customerRepository, eventPublisher);
	}
}
