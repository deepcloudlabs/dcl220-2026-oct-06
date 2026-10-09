package com.example.cm.domain.exception;

public class ValidationRule extends RuntimeException {
	private static final long serialVersionUID = 1603784728895268141L;
	private final int ruleId;

	public ValidationRule(int ruleId, String message) {
		super(message);
		this.ruleId = ruleId;
	}

	public int getRuleId() {
		return ruleId;
	}

}
