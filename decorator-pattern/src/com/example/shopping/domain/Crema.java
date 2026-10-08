package com.example.shopping.domain;

/**
 *
 * @author Binnur Kurt <binnur.kurt@gmail.com>
 */
public class Crema implements Coffee {
	private Coffee coffee;

	public Crema(Coffee coffee) {
		this.coffee = coffee;
	}

	@Override
	public double cost() {
		return coffee.cost() + 25;
	}

}
