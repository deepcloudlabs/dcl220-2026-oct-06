package com.example.domain;

public class StandardCalculator implements Calculator {

	@Override
	public double add(double x, double y) {
		System.out.println("add(%f,%f) runs.".formatted(x, y));
		return x + y;
	}

	@Override
	public double sub(double x, double y) {
		System.out.println("sub(%f,%f) runs.".formatted(x, y));
		return x - y;
	}

	@Override
	public double mul(double x, double y) {
		System.out.println("mul(%f,%f) runs.".formatted(x, y));
		return x * y;
	}

	@Override
	public double div(double x, double y) {
		System.out.println("div(%f,%f) runs.".formatted(x, y));
		return x / y;
	}

}
