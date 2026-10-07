package com.example.hr.domain;

import com.example.ddd.helper.ValueObject;

@ValueObject(boundedContext = "hr", factoryMethod = "valueOf")
public enum JobStyle {
	FULL_TIME(100), PART_TIME(200), INTERN(300), FREELANCE(400);

	private final int code;

	private JobStyle(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

}
