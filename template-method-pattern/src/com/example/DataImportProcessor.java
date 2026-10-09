package com.example;

import java.util.List;

public abstract class DataImportProcessor {

    // Template Method
    public final void process(String fileName) {

        System.out.println("Processing: " + fileName);

        String content = readFile(fileName);

        List<Customer> customers = parse(content);

        validate(customers);

        customers = transform(customers);

        save(customers);

        generateReport(customers);

        System.out.println("Processing completed.\n");
    }

    protected String readFile(String fileName) {
        System.out.println("Reading file: " + fileName);

        return "file content";
    }

    // Different for each format
    protected abstract List<Customer> parse(String content);

    // Different validation rules may apply
    protected abstract void validate(List<Customer> customers);

    // Default implementation
    protected List<Customer> transform(List<Customer> customers) {
        System.out.println("Applying common transformations...");
        return customers;
    }

    // Common implementation
    protected void save(List<Customer> customers) {
        System.out.println(
                "Saving " + customers.size() + " customers to database..."
        );
    }

    // Hook method
    protected void generateReport(List<Customer> customers) {
        // optional
    }
}