package com.example.shopping.app;

import com.example.shopping.domain.Coffee;
import com.example.shopping.domain.CoffeeLatte;
import com.example.shopping.domain.Crema;
import com.example.shopping.domain.Espresso;
import com.example.shopping.domain.LongCoffee;
import com.example.shopping.domain.Milk;
import com.example.shopping.domain.Sugar;
import com.example.shopping.domain.Tarcin;
import com.example.shopping.domain.TurkishCoffee;

/**
 *
 * @author Binnur Kurt <binnur.kurt@gmail.com>
 */
public class TestShopping {
	public static void main(String[] args) {
		Coffee latteWithCremaAndSugar = new Milk(new Sugar(new Crema(new CoffeeLatte())));
		Coffee turkishCoffeeWithStrongSugar = new LongCoffee(new Sugar(new Sugar(new Sugar(new TurkishCoffee()))));
		var coffee = new Crema(new Tarcin(new Espresso()));
		System.out.println(String.format("Cost: %-8.2f", turkishCoffeeWithStrongSugar.cost()));
		System.out.println(String.format("Cost: %-8.2f", latteWithCremaAndSugar.cost()));
		System.out.println(String.format("Cost: %-8.2f", coffee.cost()));
	}
}
