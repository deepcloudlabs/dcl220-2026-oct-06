package com.example;

public class Exercise01 {

	public static void main(String[] args) {
		DataImportProcessor csvProcessor = new CsvCustomerImportProcessor();
		csvProcessor.process("customers.csv");
		DataImportProcessor jsonProcessor = new JsonCustomerImportProcessor();
		jsonProcessor.process("customers.json");
	}

}
