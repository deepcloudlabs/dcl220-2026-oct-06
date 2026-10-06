package com.example.application;

import java.lang.reflect.Proxy;

import com.example.domain.Calculator;
import com.example.domain.StandardCalculator;
import com.example.handler.CachingHandler;

public class CalculatorApplication {

	public static void main(String[] args) {
		Calculator calculator = new StandardCalculator();
		System.err.println(calculator.getClass().getName());
		var clazz = calculator.getClass();
		calculator = (Calculator) Proxy.newProxyInstance(
				clazz.getClassLoader(), 
				clazz.getInterfaces(), 
				new CachingHandler(calculator)
		);
		System.err.println(calculator.getClass().getName());
		System.out.println("3+5: %f".formatted(calculator.add(3, 5)));
		System.out.println("3-5: %f".formatted(calculator.sub(3, 5)));
		System.out.println("3+5: %f".formatted(calculator.add(3, 5)));
		System.out.println("3+5: %f".formatted(calculator.add(3, 5)));
		System.out.println("3-5: %f".formatted(calculator.sub(3, 5)));
	}

}
