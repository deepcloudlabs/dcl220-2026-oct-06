package com.example.hr.domain;

import java.util.Base64;
import java.util.Objects;

import com.example.ddd.helper.ValueObject;

@ValueObject(boundedContext = "hr", factoryMethod = "of")
public record Photo(byte[] values) {
	public static Photo of(byte[] values) {
		Objects.requireNonNull(values);
		return new Photo(values);
	}

	public static Photo of(String values) {
		Objects.requireNonNull(values);
		return new Photo(Base64.getDecoder().decode(values));
	}

	public String toBase64() {
		return Base64.getEncoder().encodeToString(values);
	}

	@Override
	public String toString() {
		return "Photo [values=" + toBase64() + "]";
	}
	
}
