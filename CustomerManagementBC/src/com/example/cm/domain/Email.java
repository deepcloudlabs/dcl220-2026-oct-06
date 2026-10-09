package com.example.cm.domain;

import java.util.Objects;

import com.example.cm.domain.exception.ValidationRule;
import com.example.ddd.helper.ValueObject;

@ValueObject
public record Email(String value) {

	public static Email of(String value) {
		Objects.requireNonNull(value);
		if (!value.matches("[a-zA-Z09]+@[a-zA-Z09]{2,}\\.[a-zA-Z]{3,5}")) {
			throw new ValidationRule(55, "%s is not a valid e-mail.".formatted(value));
		}
		return new Email(value);
	}
}
