package com.example.hr.domain;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

class EmployeeTest {

	@ParameterizedTest
	@CsvFileSource(resources = "valid-employees.csv",maxCharsPerColumn = 65535)
	void creatingEmployee(String identity, String firstName, String lastName, String iban, double salary,
			String currency, String email, int birthYear, String jobStyle, String departments, String photo) {
		var emp = new Employee.Builder(identity)
				              .fullName(firstName, lastName)
				              .salary(salary, currency)
				              .iban(iban)
				              .birthYear(birthYear)
				              .email(email)
				              .jobStyle(jobStyle)
				              .photo(photo)
				              .departmentList(departments.split(":"))
				              .build();
		assertAll(
		    () -> assertEquals(identity,emp.getIdentity().getValue()),
		    () -> assertEquals(firstName,emp.getFullName().firstName()),
		    () -> assertEquals(lastName,emp.getFullName().lastName()),
		    () -> assertEquals(iban,emp.getIban().getValue())
		);
				              
	}

}
