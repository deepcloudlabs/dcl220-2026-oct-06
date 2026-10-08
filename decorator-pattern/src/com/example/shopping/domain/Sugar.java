package com.example.shopping.domain;

/**
 *
 * @author Binnur Kurt <binnur.kurt@gmail.com>
 */
public class Sugar implements Coffee {
	private Coffee coffee;

	public Sugar(Coffee coffee) {
		this.coffee = coffee;
	}

	@Override
	public double cost() {
		return 3.0 + coffee.cost();
	}

}
