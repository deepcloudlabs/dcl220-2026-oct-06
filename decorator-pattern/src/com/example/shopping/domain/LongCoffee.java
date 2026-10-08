package com.example.shopping.domain;

/**
 *
 * @author Binnur Kurt <binnur.kurt@gmail.com>
 */
public class LongCoffee implements Coffee {
	private Coffee coffee;

	public LongCoffee(Coffee coffee) {
		this.coffee = coffee;
	}

	public double cost() {
		return 1.5 * coffee.cost();
	}

}
