package com.example.cm.domain.exception;

public class BusinessConstraint extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private final int constraintId;

	public BusinessConstraint(int constraintId, String message) {
		super(message);
		this.constraintId = constraintId;
	}

	public int getConstraintId() {
		return constraintId;
	}
}
