package com.example;

public class PaidState implements OrderState {

    @Override
    public void pay(Order order) {
        throw new IllegalStateException(
                "Order is already paid."
        );
    }

    @Override
    public void ship(Order order) {
        System.out.println("Order shipped.");
        order.setState(new ShippedState());
    }

    @Override
    public void deliver(Order order) {
        throw new IllegalStateException(
                "Order must be shipped before delivery."
        );
    }

    @Override
    public void cancel(Order order) {
        System.out.println("Payment refunded. Order cancelled.");
        order.setState(new CancelledState());
    }

    @Override
    public String name() {
        return "PAID";
    }
}