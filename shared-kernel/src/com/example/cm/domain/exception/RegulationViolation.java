package com.example.cm.domain.exception;

public class RegulationViolation extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private final int regulationId;

	public RegulationViolation(int regulationId,String message) {
		super(message);
		this.regulationId = regulationId;
	}

	public int getRegulationId() {
		return regulationId;
	}
	
}
