package com.example.cm.domain.exception;

public class DomainInvariantViolation extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private final int invariantId;

	public DomainInvariantViolation(int invariantId, String message) {
		super(message);
		this.invariantId = invariantId;
	}

	public int getInvariantId() {
		return invariantId;
	}
}
