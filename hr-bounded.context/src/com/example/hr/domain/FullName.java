package com.example.hr.domain;

import java.util.Objects;

import com.example.ddd.helper.ValueObject;

@ValueObject(boundedContext = "hr", factoryMethod = "of")
public final record FullName(String firstName, String lastName) {
	public static FullName of(String firstName, String lastName) {
		Objects.requireNonNull(firstName);
		Objects.requireNonNull(lastName);
		if (firstName.isBlank())
			throw new IllegalArgumentException("FirstName is blank!");
		if (lastName.isBlank())
			throw new IllegalArgumentException("LastName is blank!");
		return new FullName(firstName, lastName);
	}
}
