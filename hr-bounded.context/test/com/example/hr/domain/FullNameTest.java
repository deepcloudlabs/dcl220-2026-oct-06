package com.example.hr.domain;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

class FullNameTest {

	@DisplayName("Creating FullName through of should be successful")
	@ParameterizedTest
	@CsvFileSource(resources = "valid-fullnames.csv")
	void createFullNameSuccessfully(String firstName, String lastName) {
		var fullName = FullName.of(firstName, lastName);
		assertEquals(firstName, fullName.firstName());
		assertEquals(lastName, fullName.lastName());
	}

	@DisplayName("Creating Empty FullName should fail")
	@ParameterizedTest
	@CsvFileSource(resources = "invalid-fullnames.csv")
	void creatingEmptyFullNameShoudlFail(String firstName, String lastName) {
		assertThrows(RuntimeException.class, () -> FullName.of(firstName, lastName));
	}

}
