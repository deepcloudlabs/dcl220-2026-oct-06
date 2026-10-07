package com.example.hr.domain;

import java.util.Objects;

import com.example.ddd.helper.ValueObject;

@ValueObject(boundedContext = "hr", factoryMethod = "valueOf")
public final class Iban {
	private static final long MAX = 999999999;
	private static final long MODULUS = 97;
	private final String value;

	private Iban(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static Iban valueOf(String value) {
		if (!isValid(value))
			throw new IllegalArgumentException("%s is not a valid iban!".formatted(value));
		return new Iban(value);
	}

	private static boolean isValid(String value) {
		if (value == null || value.length() < 5) {
			return false;
		}
		try {
			int modulusResult = calculateModulus(value);
			return (modulusResult == 1);
		} catch (Exception ex) {
			return false;
		}
	}

	private static int calculateModulus(String code) {
		String reformattedCode = code.substring(4) + code.substring(0, 4);
		long total = 0;
		for (int i = 0; i < reformattedCode.length(); i++) {
			int charValue = Character.getNumericValue(reformattedCode.charAt(i));
			if (charValue < 0 || charValue > 35) {
				throw new IllegalArgumentException("Invalid Character[" + i + "] = '" + charValue + "'");
			}
			total = (charValue > 9 ? total * 100 : total * 10) + charValue;
			if (total > MAX) {
				total = (total % MODULUS);
			}
		}
		return (int) (total % MODULUS);
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Iban other = (Iban) obj;
		return Objects.equals(value, other.value);
	}

	@Override
	public String toString() {
		return "Iban [value=" + value + "]";
	}

}
