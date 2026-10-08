package com.example;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.example.service.Calculator;

public class App {
	public static void main(String[] args) {
		ConfigurableApplicationContext container = new AnnotationConfigApplicationContext("com.example.config");
		Calculator calc = container.getBean(Calculator.class);
		System.out.println(calc.getClass());
		calc.add(3, 5);
		calc.sub(7, 4);
		calc.mul(3, 2);
		calc.div(5, 3);
		try {
			calc.div(4, 0);
		} catch (Exception e) {
		}
		container.close();
	}
}
