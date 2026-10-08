package com.example.shopping.domain;

/**
 *
 * @author Binnur Kurt <binnur.kurt@gmail.com>
 */
public class Milk implements Coffee {
	private Coffee coffee;

	public Milk(Coffee coffee) {
		this.coffee = coffee;
	}

	@Override
	public double cost() {
		return 10.0 + coffee.cost();
	}

}
