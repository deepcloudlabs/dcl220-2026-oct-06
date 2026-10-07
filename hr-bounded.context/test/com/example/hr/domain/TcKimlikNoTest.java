package com.example.hr.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

class TcKimlikNoTest {

	@DisplayName("Creating TcKimlikNo through valueOf should be successful")
	@ParameterizedTest
	@CsvFileSource(resources = "valid-tckimlikno.csv")
	void valueOfCreatesTcKimlikNoSuccessfully(String value) {
		var identity = TcKimlikNo.valueOf(value);
		assertEquals(value, identity.getValue());
	}

	@DisplayName("Creating TcKimlikNo through valueOf should fail")
	@ParameterizedTest
	@CsvFileSource(resources = "invalid-tckimlikno.csv")
	void valueOfThrowsIllegalArgumentException(String value) {
		assertThrows(IllegalArgumentException.class, () -> TcKimlikNo.valueOf(value));
	}

}
