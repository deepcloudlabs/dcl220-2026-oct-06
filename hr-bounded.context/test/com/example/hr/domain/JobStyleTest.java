package com.example.hr.domain;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

class JobStyleTest {

	@DisplayName("Creating TcKimlikNo through valueOf should fail")
	@ParameterizedTest
	@CsvFileSource(resources = "valid-jobstyles.csv")
	void valueOfCreatesJobStyleSuccessfully(String label, int code, int ordinal) {
		var intern = JobStyle.valueOf(label);
		assertEquals(code, intern.getCode());
		assertEquals(ordinal, intern.ordinal());
	}

}
