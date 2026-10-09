package com.example;

/*
             pay
    NEW -------------> PAID
     |                   |
     | cancel            | ship
     v                   v
CANCELLED             SHIPPED
                         |
                         | deliver
                         v
                     DELIVERED
*/

public class Exercise01 {
	public static void main(String[] args) {
		Order order = new Order();

		System.out.println(order.getState());

		order.pay();
		System.out.println(order.getState());

		order.ship();
		System.out.println(order.getState());

		order.deliver();
		System.out.println(order.getState());
	}
}
