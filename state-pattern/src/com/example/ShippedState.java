package com.example;

public class ShippedState implements OrderState {

    @Override
    public void pay(Order order) {
        throw new IllegalStateException(
                "Order is already paid."
        );
    }

    @Override
    public void ship(Order order) {
        throw new IllegalStateException(
                "Order is already shipped."
        );
    }

    @Override
    public void deliver(Order order) {
        System.out.println("Order delivered.");
        order.setState(new DeliveredState());
    }

    @Override
    public void cancel(Order order) {
        throw new IllegalStateException(
                "A shipped order cannot be cancelled."
        );
    }

    @Override
    public String name() {
        return "SHIPPED";
    }
}