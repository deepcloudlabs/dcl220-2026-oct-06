package com.example.cm.domain;

import java.util.Objects;

import com.example.cm.domain.exception.ValidationRule;
import com.example.ddd.helper.Identity;
import com.example.ddd.helper.ValueObject;

// Immutable, Identity -> value
@ValueObject
@Identity(entity = Customer.class)
public final class CustomerID {
	private final String value;
	
	private CustomerID(String value) {
		this.value = value;
	}
	
	// factory method -> guarantees to return valid object
	public static CustomerID of(String id) {
		validate(id);
		return new CustomerID(id);
	}
	
	public static void validate(String value) {
		if (isValid(value))
			return;
		throw new ValidationRule(42,"%s is not a valid identity number".formatted(value));
	}

	public static boolean isValid(String value) {
		return true;
	}

	// String: immutable
	public String getValue() {
		return value;
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
		CustomerID other = (CustomerID) obj;
		return Objects.equals(value, other.value);
	}

	@Override
	public String toString() {
		return "CustomerID [id=" + value + "]";
	}

}
