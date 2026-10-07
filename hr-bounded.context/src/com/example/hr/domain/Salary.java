package com.example.hr.domain;

import java.util.Objects;

import com.example.ddd.helper.ValueObject;

@ValueObject(boundedContext = "hr", factoryMethod = "of")
public record Salary(double value, FiatCurrency currency) {
	public static Salary of(double value, FiatCurrency currency) {
		Objects.requireNonNull(currency);
		if (value <= 0.0)
			throw new IllegalArgumentException("Salary must be positive: %f".formatted(value));
		return new Salary(value, currency);
	}

	public static Salary of(double value) {
		if (value <= 0.0)
			throw new IllegalArgumentException("Salary must be positive: %f".formatted(value));
		return new Salary(value, FiatCurrency.TL);
	}

	public boolean greaterThanOrEqual(Salary salary) {
		return value > salary.value;
	}

	public Salary multiplies(double scale) {
		return Salary.of(value * scale, currency);
	}

	public Salary multiplies(Rate rate) {
		return new Salary(value*(1.0+rate.value()/100), currency);
	}

	public boolean lessThanOrEqual(Salary salary) {
		return value < salary.value;

	}
}
