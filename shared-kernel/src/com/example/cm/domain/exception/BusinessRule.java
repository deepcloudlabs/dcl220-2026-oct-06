package com.example.cm.domain.exception;

public class BusinessRule extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private final int ruleId;

	public BusinessRule(int ruleId, String message) {
		super(message);
		this.ruleId = ruleId;
	}

	public int getRuleId() {
		return ruleId;
	}
}
