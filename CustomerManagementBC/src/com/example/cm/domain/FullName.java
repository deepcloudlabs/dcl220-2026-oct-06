package com.example.cm.domain;

import java.util.Objects;

import com.example.cm.domain.exception.ValidationRule;
import com.example.ddd.helper.ValueObject;

@ValueObject
public record FullName(String firstName, String lastName) {
	public static FullName valueOf(String firstName, String lastName) {
		Objects.requireNonNull(firstName);
		Objects.requireNonNull(lastName);
		if (firstName.length() < 2)
			throw new ValidationRule(100,"%s should have at least two characters".formatted(firstName));
		if (lastName.length() < 2)
			throw new ValidationRule(100,"%s should have at least two characters".formatted(lastName));
		return new FullName(firstName, lastName);
	}
}
