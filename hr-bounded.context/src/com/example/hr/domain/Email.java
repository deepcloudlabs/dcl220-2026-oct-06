package com.example.hr.domain;

import java.util.Objects;

import com.example.ddd.helper.ValueObject;

@ValueObject(boundedContext = "hr", factoryMethod = "valueOf")
public record Email(String value) {
	public static Email valueOf(String value) {
		Objects.requireNonNull(value);
		if (!value.matches("\\w+@\\w+\\.\\w{2,5}"))
			throw new IllegalArgumentException("%s is not a valid e-mail.".formatted(value));
		return new Email(value);
	}
}
