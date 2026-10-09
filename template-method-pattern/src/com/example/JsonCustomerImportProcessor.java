package com.example;

import java.util.List;

public class JsonCustomerImportProcessor extends DataImportProcessor {

	@Override
	protected List<Customer> parse(String content) {
		System.out.println("Parsing JSON data...");
		// Normally Jackson/Gson would be used here
		return List.of(new Customer(10, "Carol", "carol@example.com"), new Customer(11, "David", "david@example.com"));
	}

	@Override
	protected void validate(List<Customer> customers) {
		System.out.println("Validating JSON records...");
		for (Customer customer : customers) {
			if (customer.id() <= 0) {
				throw new IllegalArgumentException("Customer ID must be positive.");
			}
			if (customer.name() == null || customer.name().isBlank()) {
				throw new IllegalArgumentException("Customer name is required.");
			}
		}
	}
}
