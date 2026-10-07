package com.example.hr.domain;

import com.example.ddd.helper.ValueObject;

@ValueObject(boundedContext = "hr")
public record BirthYear(int value) {
	
}
