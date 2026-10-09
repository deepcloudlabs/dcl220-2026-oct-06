package com.example.cm.domain;

import java.time.ZonedDateTime;

import com.example.cm.domain.exception.RegulationViolation;

public record BirthDate(int day, int month, int year) {

	public static BirthDate of(int day, int month, int year) {
		// TODO: validate day/month/year
		if (getAge(year) < 18)
			throw new RegulationViolation(30, "Age must be larger than or equal to 18");
		return new BirthDate(day, month, year);
	}

	private static int getAge(int year) {
		return ZonedDateTime.now().getYear() - year;
	}
}
