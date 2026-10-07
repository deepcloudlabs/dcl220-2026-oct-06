package com.example.hr.domain;

import com.example.ddd.helper.ValueObject;

@ValueObject(boundedContext = "hr", factoryMethod = "valueOf") 
public enum Department {
	HR, SALES, FINANCE, ACCOUNTING, IT, AI
}
