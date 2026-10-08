package com.example.shopping.domain;

public class Tarcin implements Coffee {
	private final Coffee coffee;
	
	public Tarcin(Coffee coffee) {
		this.coffee = coffee;
	}

	@Override
	public double cost() {
		return 30 + coffee.cost();
	}

}
