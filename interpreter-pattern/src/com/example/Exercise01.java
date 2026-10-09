package com.example;

import java.math.BigDecimal;

record Customer(String country, boolean vip, boolean premium, BigDecimal total) {
}

interface Expression {
	boolean interpret(Customer customer);
}

class VipExpression implements Expression {

	@Override
	public boolean interpret(Customer customer) {
		return customer.vip();
	}
}

class PremiumExpression implements Expression {

	@Override
	public boolean interpret(Customer customer) {
		return customer.premium();
	}
}

class CountryExpression implements Expression {

	private final String expectedCountry;

	public CountryExpression(String expectedCountry) {
		this.expectedCountry = expectedCountry;
	}

	@Override
	public boolean interpret(Customer customer) {
		return expectedCountry.equalsIgnoreCase(customer.country());
	}
}

class MinimumTotalExpression implements Expression {

	private final BigDecimal minimum;

	public MinimumTotalExpression(BigDecimal minimum) {
		this.minimum = minimum;
	}

	@Override
	public boolean interpret(Customer customer) {
		return customer.total().compareTo(minimum) >= 0;
	}
}

class AndExpression implements Expression {

	private final Expression left;
	private final Expression right;

	public AndExpression(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public boolean interpret(Customer customer) {
		return left.interpret(customer) && right.interpret(customer);
	}
}

class OrExpression implements Expression {

	private final Expression left;
	private final Expression right;

	public OrExpression(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public boolean interpret(Customer customer) {
		return this.left.interpret(customer) || this.right.interpret(customer);
	}
}

class NotExpression implements Expression {

	private final Expression expression;

	public NotExpression(Expression expression) {
		this.expression = expression;
	}

	@Override
	public boolean interpret(Customer customer) {
		return !expression.interpret(customer);
	}
}

public class Exercise01 {

	public static void main(String[] args) {

		/*
		 * Business Rule:
		 *
		 * (VIP AND TOTAL >= 1000) OR (PREMIUM AND COUNTRY = TR)
		 */

		Expression rule = new OrExpression(
				new AndExpression(new VipExpression(), new MinimumTotalExpression(new BigDecimal("1000"))),
				new AndExpression(new PremiumExpression(), new CountryExpression("TR"))
		);

		Customer customer1 = new Customer("US", true, false, new BigDecimal("1500"));

		Customer customer2 = new Customer("TR", false, true, new BigDecimal("300"));

		Customer customer3 = new Customer("DE", false, false, new BigDecimal("5000"));

		evaluate(rule, customer1);
		evaluate(rule, customer2);
		evaluate(rule, customer3);
	}

	private static void evaluate(Expression rule, Customer customer) {

		System.out.printf("%-50s eligible: %s%n", customer, rule.interpret(customer));
	}
}