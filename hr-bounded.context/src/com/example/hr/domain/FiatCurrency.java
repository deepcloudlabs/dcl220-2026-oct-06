package com.example.hr.domain;

import com.example.ddd.helper.ValueObject;

@ValueObject(boundedContext = "hr", factoryMethod = "valueOf")
public enum FiatCurrency {
	TL, EUR, USD
}
