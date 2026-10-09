package com.example.cm.domain;

import java.util.Objects;

import com.example.cm.domain.exception.ValidationRule;
import com.example.ddd.helper.ValueObject;

@ValueObject
public record AddressLines(String... values) {
	public static AddressLines of(String... values) {
		Objects.requireNonNull(values);
		for (var value : values) {
			if (value.length() < 2)
				throw new ValidationRule(66, "Address line (%s) should have at least two char length".formatted(value));
		}
		return new AddressLines(values);
	}
}
