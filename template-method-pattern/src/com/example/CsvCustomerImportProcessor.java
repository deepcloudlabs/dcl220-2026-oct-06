package com.example;

import java.util.List;

public class CsvCustomerImportProcessor extends DataImportProcessor {

	@Override
	protected List<Customer> parse(String content) {
		System.out.println("Parsing CSV data...");
		// Normally:
		// split lines
		// split columns
		// map fields
		return List.of(new Customer(1, "Alice", "alice@example.com"), new Customer(2, "Bob", "bob@example.com"));
	}

	@Override
	protected void validate(List<Customer> customers) {
		System.out.println("Validating CSV records...");
		for (Customer customer : customers) {
			if (customer.email() == null || !customer.email().contains("@")) {
				throw new IllegalArgumentException("Invalid email: " + customer.email());
			}
		}
	}

	@Override
	protected void generateReport(List<Customer> customers) {
		System.out.println("CSV import report generated for " + customers.size() + " records.");
	}
}