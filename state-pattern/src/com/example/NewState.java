package com.example;

public class NewState implements OrderState {

    @Override
    public void pay(Order order) {
        System.out.println("Payment received.");
        order.setState(new PaidState());
    }

    @Override
    public void ship(Order order) {
        throw new IllegalStateException(
                "A new order cannot be shipped before payment."
        );
    }

    @Override
    public void deliver(Order order) {
        throw new IllegalStateException(
                "A new order cannot be delivered."
        );
    }

    @Override
    public void cancel(Order order) {
        System.out.println("Order cancelled.");
        order.setState(new CancelledState());
    }

    @Override
    public String name() {
        return "NEW";
    }
}