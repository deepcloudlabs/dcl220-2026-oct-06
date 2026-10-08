package com.example.service.impl;

import java.util.concurrent.TimeUnit;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.example.aop.Profile;
import com.example.service.Calculator;

/**
 * 
 * @author Binnur Kurt <binnur.kurt@gmail.com>
 *
 */
@Service
@Lazy
@Scope("singleton")
public class SimpleCalculator implements Calculator {

	
	public SimpleCalculator() {
		System.err.println("SimpleCalculator()");
	}

	@Override
	@Profile(TimeUnit.NANOSECONDS)
	@Cacheable
	public double add(double x, double y) {
		return x + y;
	}

	@Override
	@Profile(TimeUnit.NANOSECONDS)
	public double sub(double x, double y) {
		return x - y;
	}

	@Override
	@Profile(TimeUnit.NANOSECONDS)
	public double mul(double x, double y) {
		return x * y;
	}

	@Override
	public double div(double x, double y) {
		if (y == 0.0)
			throw new IllegalArgumentException("divisor cannot be zero!");
		return x / y;
	}

}
